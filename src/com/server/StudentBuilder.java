package com.server;

import java.util.Map;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;

public class StudentBuilder implements IStudentBuilder{

	private Student student;
	
	public StudentBuilder(){
		student = new Student();
	}
	
	@Override
	public IStudentBuilder setId(final Long id) {
		student.setId(id);
		return this;
	}

	@Override
	public IStudentBuilder setName(final String name) {
		student.setName(name);
		return this;
	}

	@Override
	public IStudentBuilder setType(final Type type) {
		student.setType(type);
		return this;
	}

	@Override
	public IStudentBuilder setGender(final Gender gender) {
		student.setGender(gender);
		return this;
	}
	
	@Override
	public IStudentBuilder setStudentBasedOnOptions(Map<String, String> options)
			throws BadRequestException{
		
		Long id = new Long(options.get("id"));
		String name = options.get("name");
		String genderText =  options.get("gender");
		Gender gender = Gender.getGender(genderText);
		String typeText = options.get("type");
		Type type = Type.getType(typeText);
		
		student.setId(id);
		student.setName(name);
		student.setGender(gender);
		student.setType(type);

		if (student.getName() == null 
			|| student.getName().equalsIgnoreCase("") 
			|| student.getGender() == null 
			|| student.getType() == null) {
			
			throw new BadRequestException("Missing name, gender or type");
		}
		
		return this;
	}
	
	public Student build(){
		return student;
	}
}

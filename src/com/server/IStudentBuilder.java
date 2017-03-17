package com.server;

import java.util.Map;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;

public interface IStudentBuilder {

	public IStudentBuilder setId(final Long id);
	
	public IStudentBuilder setName(final String name);
	
	public IStudentBuilder setType(final Type type);
	
	public IStudentBuilder setGender(final Gender gender);
	
	public IStudentBuilder setStudentBasedOnOptions(Map<String, String> options) throws BadRequestException;
	
	public Student build();
}

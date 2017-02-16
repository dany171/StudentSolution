package com.server.data;

import java.util.Date;
import java.util.HashMap;

import com.model.Student;

public class FileDataService implements DataService{

	
	HashMap<Long, Student> students = new HashMap<Long,Student>();
	Long lastId = 1L;
	@Override
	public Student save(Student student) {
		if(students.isEmpty()){
			student.setId(lastId);
		}else{
			lastId++;
			student.setId(lastId);
		}
		Date d = new Date();
		student.setTimestamp(d.getTime());
		students.put(student.getId(),student);
		System.out.println("user saved");
		return student;		
	}

	@Override
	public Student update(Student student) {
		Date d = new Date();
		student.setTimestamp(d.getTime());
		students.put(student.getId(), student);
		System.out.println("user updated");		
		return student;
	}

	@Override
	public void delete(Long id) {
		students.remove(id);
		System.out.println("User deleted");
	}	
}

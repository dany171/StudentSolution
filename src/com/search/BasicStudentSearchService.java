package com.search;

import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public class BasicStudentSearchService implements StudentSearchService {

	@Override
	public Student searchByName(String name,Map<String, Student> studentsByName) {
	
		return studentsByName.get(name);
	}

	@Override
	public Student[] searchByType(Type type) {
		System.out.println("searchByType");
		return null;
	}

	@Override
	public Student[] searchByTypeAndGender(Type type, Gender gender) {
		System.out.println("searchByTypeAndGender");
		return null;
	}

}

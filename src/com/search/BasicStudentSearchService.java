package com.search;

import java.util.Collection;
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
	public Collection<Student> searchByGender(Gender gender,
			Map<Gender, Collection<Student>> studentsByGender) {
		return studentsByGender.get(gender);
	}

}

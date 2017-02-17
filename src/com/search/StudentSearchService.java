package com.search;

import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public interface StudentSearchService {
	public Student searchByName(String name, Map<String,Student> studentsByName);
	public Student[] searchByType(Type type);
	public Student[] searchByTypeAndGender(Type type, Gender gender);
}

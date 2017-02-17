package com.search;

import java.util.Collection;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public interface StudentSearchService {
	public Student searchByName(String name, Map<String,Student> studentsByName);
	public Student[] searchByType(Type type);
	public Collection<Student> searchByGender(Gender gender,Map<Gender,Collection<Student>> studentsByGender);
}

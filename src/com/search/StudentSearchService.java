package com.search;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public interface StudentSearchService {
	public Student[] searchByName(String name);
	public Student[] searchByType(Type type);
	public Student[] searchByTypeAndGender(Type type, Gender gender);
}

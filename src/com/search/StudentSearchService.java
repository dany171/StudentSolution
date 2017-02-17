package com.search;

import java.util.Collection;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;
import com.server.data.PropertyTypeMap;

public interface StudentSearchService {
	
	public Student searchByName(String name, Map<String,Student> studentsByName);
	
	public Collection<Student> searchByType(Type type, PropertyTypeMap<Type> studentsByType);
	
	public Collection<Student> searchByGender(Gender gender, PropertyTypeMap<Gender> studentsByGender);
	
	public Collection<Student> searchByTypeAndGender(
			Type type,
			Gender gender,
			PropertyTypeMap<Type> studentsByType,
			PropertyTypeMap<Gender> studentsByGender
	);	
}
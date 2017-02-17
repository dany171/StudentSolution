package com.search;

import java.util.Collection;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;
import com.server.data.PropertyTypeMap;

public class BasicStudentSearchService implements StudentSearchService {

	@Override
	public Student searchByName(String name,Map<String, Student> studentsByName) {
		return studentsByName.get(name);
	}

	@Override
	public Collection<Student> searchByType(Type type, PropertyTypeMap<Type> studentsByType) {
		return studentsByType.getPropertyCollection(type);
	}

	@Override
	public Collection<Student> searchByGender(Gender gender,
			Map<Gender, Collection<Student>> studentsByGender) {
		return studentsByGender.get(gender);
	}

}

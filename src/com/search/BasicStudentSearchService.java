package com.search;

import java.util.ArrayList;
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
	public Collection<Student> searchByGender(Gender gender, PropertyTypeMap<Gender> studentsByGender) {
		return studentsByGender.getPropertyCollection(gender);
	}

	@Override
	public Collection<Student> searchByTypeAndGender(Type type, Gender gender,
			PropertyTypeMap<Type> studentsByType,
			PropertyTypeMap<Gender> studentsByGender) {
		
		Collection<Student> byType = studentsByType.getPropertyCollection(type);
		Collection<Student> byGender = studentsByGender.getPropertyCollection(gender);
		
		Collection<Student> res = new ArrayList<Student>();
		
		for (Student s : byType) {
		   if(byGender.contains(s)) {
		       res.add(s);
		   }
		}
		
		return res;
	}

	

}

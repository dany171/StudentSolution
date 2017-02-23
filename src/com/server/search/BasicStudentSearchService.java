package com.server.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.StudentIndexByName;
import com.server.data.StudentCatalogs;

public class BasicStudentSearchService implements StudentSearchService {

	// CONSTANTS
	private final NameComparator STUDENT_NAME_COMPARATOR = new NameComparator();
	
	@Override
	public Collection<Student> searchByName(String name, StudentIndexByName studentsByName) {
		
		TreeMap<Long,Student> studentsTree = (TreeMap<Long, Student>) studentsByName.get(name);
		List<Student> students = new ArrayList<Student>(studentsTree.values());
		Collections.sort(students, STUDENT_NAME_COMPARATOR);
		
		return students;
	}

	@Override
	public Collection<Student> searchByType(Type type,
			StudentCatalogs<Type> studentsByType) {
				
		Collection<Student> students = studentsByType.getCatalog(type);
		List<Student> res = new ArrayList<Student>(students);
		Collections.sort(res);
		
		return res;
	}

	@Override
	public Collection<Student> searchByGender(Gender gender,
			StudentCatalogs<Gender> studentsByGender) {
		
		Collection<Student> students = studentsByGender.getCatalog(gender);
		
		List<Student> res = new ArrayList<Student>(students);
		System.out.println(res);
		
		Collections.sort(res);		
		System.out.println(res);

		return res;
	}

	@Override
	public Collection<Student> searchByTypeAndGender(Type type, Gender gender,
			StudentCatalogs<Type> studentsByType,
			StudentCatalogs<Gender> studentsByGender) {

		Collection<Student> byType = studentsByType.getCatalog(type);
		Collection<Student> byGender = studentsByGender.getCatalog(gender);
		Collection<Student> students = new ArrayList<Student>();

		for (Student s : byType) {
			if (byGender.contains(s)) { students.add(s); }
		}
		
		List<Student> res = new ArrayList<Student>(students);
		Collections.sort(res);

		return res;
	}
	
	/**
	 * Compares student names
	 * 
	 * @author Daniel Echalar
	 *
	 */
	private  class NameComparator implements Comparator<Student>{
		@Override
		public int compare(Student o1, Student o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
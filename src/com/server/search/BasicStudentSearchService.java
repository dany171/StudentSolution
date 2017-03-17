package com.server.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
import com.server.data.StudentIndexByName;
import com.server.data.StudentCatalogs;

public class BasicStudentSearchService implements StudentSearchService {
	
	// CONSTANTS
	private final NameComparator STUDENT_NAME_COMPARATOR = new NameComparator();
	
	@Override
	public Collection<Student> search(Criteria criteria, DataService dataService) {
		
		if(criteria.hasName()){
			StudentIndexByName studentsByName = dataService.getStudentsByName();
			
			String name = criteria.getName();
			TreeMap<Long,Student> studentsTree = (TreeMap<Long, Student>) studentsByName.get(name);
			
			List<Student> students = new ArrayList<Student>(studentsTree.values());
			Collections.sort(students, STUDENT_NAME_COMPARATOR);
			
			return students;
		}
		
		if(criteria.hasType() && criteria.hasGender()){
			
			StudentCatalogs<Type> studentsByType = dataService.getStudentsByType();
			StudentCatalogs<Gender> studentsByGender = dataService.getStudentsByGender();
			
			Type type = criteria.getType();
			Gender gender = criteria.getGender();
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
		
		if(criteria.hasType()){
			StudentCatalogs<Type> studentsByType = dataService.getStudentsByType();
			
			Type type = criteria.getType();
			Collection<Student> students = studentsByType.getCatalog(type);
			List<Student> res = new ArrayList<Student>(students);
			Collections.sort(res);
			return res;
		}
		
		if(criteria.hasGender()){
			StudentCatalogs<Gender> studentsByGender = dataService.getStudentsByGender();
			
			Gender gender = criteria.getGender();
			Collection<Student> students = studentsByGender.getCatalog(gender);
			List<Student> res = new ArrayList<Student>(students);
			Collections.sort(res);		

			return res;
		}
		
		return null;
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
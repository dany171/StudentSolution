package com.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;
import com.server.data.InvertedIndexTreeByName;
import com.server.data.PropertyTypeMap;

public class BasicStudentSearchService implements StudentSearchService {

	private final static SortByName sortByName = new SortByName();
	
	@Override
	public Collection<Student> searchByName(String name, InvertedIndexTreeByName studentsByName) {
		
		TreeMap<Long,Student> studentsTree = studentsByName.get(name);
		
		List<Student> students = new ArrayList<Student>(studentsTree.values());
				
		Collections.sort(students, BasicStudentSearchService.sortByName);
		
		return students;
	}

	/*public Student searchByNameSorted(String name, Map<String, Student> studentsByName) {
		
		TreeMap<String,Student> sorted = new TreeMap<String,Student>(); 
		return studentsByName.get(name);
	}*/
	
	@Override
	public Collection<Student> searchByType(Type type,
			PropertyTypeMap<Type> studentsByType) {
				
		Collection<Student> students = studentsByType.getPropertyCollection(type);
		
		List<Student> res = new ArrayList<Student>(students);
		
		Collections.sort(res);
		
		return res;
	}

	@Override
	public Collection<Student> searchByGender(Gender gender,
			PropertyTypeMap<Gender> studentsByGender) {
		
		Collection<Student> students = studentsByGender.getPropertyCollection(gender);
		
		List<Student> res = new ArrayList<Student>(students);
		System.out.println(res);
		
		Collections.sort(res);		
		
		System.out.println(res);
		return res;
	}

	@Override
	public Collection<Student> searchByTypeAndGender(Type type, Gender gender,
			PropertyTypeMap<Type> studentsByType,
			PropertyTypeMap<Gender> studentsByGender) {

		Collection<Student> byType = studentsByType.getPropertyCollection(type);
		
		Collection<Student> byGender = studentsByGender.getPropertyCollection(gender);

		Collection<Student> students = new ArrayList<Student>();

		for (Student s : byType) {
			
			if (byGender.contains(s)) { students.add(s); }
		}
		
		List<Student> res = new ArrayList<Student>(students);
		
		Collections.sort(res);

		return res;
	}
	
	private  static class SortByName implements Comparator<Student>{
		@Override
		public int compare(Student o1, Student o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

}
/*
-se crea un estudiante
-se toma el nombre y de los parte, es decir, dado "dan", se ingresan 3 elementos como keys a un map/tree:
	d - da - dan
	
-A cada key se le asigna un value del tipo array que contiene students que contienen el nombre o parte de el
*/
package com.server.data;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.model.Student;

public class FileDataService implements DataService{

	Long lastId = 1L;
	
	HashMap<Long, Student> students = new HashMap<Long,Student>();
	
	HashMap<String, Student> byName = new HashMap<String,Student>();
	
	@Override
	public Student save(Student student) {
		if(students.isEmpty()){
			student.setId(lastId);
		}else{
			lastId++;
			student.setId(lastId);
		}
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(),student);
		byName.put(student.getName(),student);
		
		System.out.println("user saved");
		return student;		
	}

	@Override
	public Student update(Student student) {
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(), student);
		byName.put(student.getName(),student);
		
		System.out.println("user updated");		
		return student;
	}

	@Override
	public void delete(Long id) {
		
		Student student = students.get(id);
		byName.remove(student.getName());
		
		students.remove(id);
		
		System.out.println("User deleted");
	}	
	
	private Map<String,Student> createStudentsByName(Map<Long, Student> original){
		
		Map<String,Student> byName = new HashMap<String,Student>(original.size());
		Collection<Student> students = original.values();
		Iterator<Student> i = students.iterator();
		
		while(i.hasNext()){
			Student student = i.next();
			byName.put(student.getName(), student);
		}
		return byName;		
	}
	
	public Map<String, Student> getStudentsByName(){		
		return byName;		
	}
	
	
}

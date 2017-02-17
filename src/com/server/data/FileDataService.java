package com.server.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;

public class FileDataService implements DataService{

	Long lastId = 1L;
	
	Map<Long, Student> students = new HashMap<Long,Student>();
	
	Map<String, Student> byName = new HashMap<String,Student>();
	
	Collection<Student> males = new ArrayList<Student>();
	Collection<Student> females = new ArrayList<Student>();
	Map<Gender,Collection<Student>> byGender = new HashMap<Gender,Collection<Student>>();
	
	public FileDataService(){
		byGender.put(Gender.MALE, males);
		byGender.put(Gender.FEMALE, females);
	}
	
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
		//gender
		if(student.getGender()==Gender.MALE){
			males.add(student);
		}else{
			females.add(student);
		}		 
		
		System.out.println("user saved");
		return student;		
	}

	@Override
	public Student update(Student student) {
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(), student);
		byName.put(student.getName(),student);
		
		//gender
		Student oldStudent = students.get(student.getId());
		if(oldStudent.getGender()==student.getGender()){
			if(student.getGender()==Gender.MALE){
				males.remove(oldStudent);
				males.add(student);
			}else{
				females.remove(oldStudent);
				females.add(student);
			}	
		}else{
			if(student.getGender()==Gender.MALE){
				females.remove(oldStudent);
				males.add(student);
			}else{
				males.remove(oldStudent);
				females.add(student);
			}
		}
		
		System.out.println("user updated");		
		return student;
	}

	@Override
	public void delete(Long id) {
		
		Student student = students.get(id);
		byName.remove(student.getName());
		
		students.remove(id);
				
		if(student.getGender()==Gender.MALE){
			males.remove(student);
		}else{
			females.remove(student);
		}	
		
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
	
	public Map<Gender,Collection<Student>> getStudentsByGender(){
		return byGender;
	}

	@Override
	public boolean persist(String filename) {
		PrintWriter writer =null;
		boolean res = false;
		try{
			Iterator<Student> i = students.values().iterator();
			writer = new PrintWriter(filename, "UTF-8");
			while(i.hasNext()){
				
			    writer.println(i.next().toString());
			    
			}
			writer.close();
			res = true;
		} catch (IOException e) {
		   System.out.println(e);
		   if(writer!=null){
			   writer.close();
		   }
		   res = false;
		}
		return res;
	}	
}

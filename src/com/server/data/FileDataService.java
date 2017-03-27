package com.server.data;

import java.util.Collection;
import java.util.Date;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.structures.StudentCatalogs;
import com.server.data.structures.StudentDataHolder;
import com.server.data.structures.StudentIndexByName;

public class FileDataService implements DataService {

	private Long lastId = 1L;
	
	private final FileDataPersister fileDataPersister = new FileDataPersister();
	private final FileDataLoader fileDataLoader = new FileDataLoader();
	
	private StudentDataHolder students;
	
	public FileDataService() {
		students = new StudentDataHolder();
	}

	@Override
	public synchronized Student save(Student student) {
		if (!students.isEmpty()) {
			lastId++;
		}		
		student.setId(lastId);
		
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student);
		
		System.out.println("user saved");
		return student;
	}

	@Override
	public synchronized Student update(Student student) {
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student);

		System.out.println("user updated");
		return student;
	}

	@Override
	public synchronized void delete(Long id) {
		Student student = students.get(id);
		students.remove(student);
		System.out.println("User deleted");
	}
		
	public StudentIndexByName getStudentsByName() { 
		return students.getStudentsIndexedByName(); 
	}
	
	public StudentCatalogs<Gender> getStudentsByGender() { 
		return students.getStudentsCatalogedByGender();	
	}
	public StudentCatalogs<Type> getStudentsByType() { 
		return students.getStudentsCatalogedByType();
	}
	public void setStudentsByType(StudentCatalogs<Type> byType) { 
		students.setStudentsCatalogedByType(byType);	
	}

	@Override
	public synchronized boolean persist(String filename) {
		Collection<Student> values = students.values();
		return fileDataPersister.persist(filename, values);
	}

	@Override
	public synchronized boolean load(String filename) {
		boolean loaded = fileDataLoader.load(filename, students);
		setLastId();
		return loaded;
	}
		
	private void setLastId(){
		Collection<Student> values = students.values();
		for(Student s : values){
			if(s.getId() > lastId){
				lastId = s.getId();
			}
		}
	}
}
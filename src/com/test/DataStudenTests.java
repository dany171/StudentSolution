package com.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
import com.server.data.FileDataServiceBuilder;

public class DataStudenTests extends TestCase {
	
	private DataService fileDataService;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		FileDataServiceBuilder fileDataServiceBuilder = new FileDataServiceBuilder();
		fileDataService = fileDataServiceBuilder.getResult();
	}
	
	@Test 
	public void testSaveStudent(){
		Student student = instantiateStudent();
		Student savedStudent = fileDataService.save(student);
		
		Long id = savedStudent.getId();
		assertNotNull(id);

		Long timestamp = savedStudent.getTimestamp();
		assertNotNull(timestamp);
	}
	
	public void testUpdateStudent(){
		Student student = instantiateStudent();
		Student savedStudent = fileDataService.save(student);

		Long savedStudentId = savedStudent.getId();
		
		String newName = "maria";
		Gender newGender = Gender.FEMALE;
		Type newtype = Type.ELEMENTARY;
		
		savedStudent.setName(newName);
		savedStudent.setGender(newGender);
		savedStudent.setType(newtype);
		
		Student updatedStudent = fileDataService.update(savedStudent);
		
		Long updatedStudentId = updatedStudent.getId();
		assertEquals(savedStudentId, updatedStudentId);
		
		String updatedStudentName = updatedStudent.getName();
		
		assertEquals(updatedStudentName, newName);
	}
	
	private Student instantiateStudent(){
		String name = "daniel";
		Gender gender = Gender.MALE;
		Type type = Type.KINDERGARDEN;
		
		Student student = new Student();
		student.setName(name);
		student.setGender(gender);
		student.setType(type);
		
		return student;
	}
	
	public void testDeleteStudent(){
		Student student = instantiateStudent();
		Student savedStudent = fileDataService.save(student);
		
		Long id = savedStudent.getId();
		assertNotNull(id);
		
		fileDataService.delete(id);
		
	}
}

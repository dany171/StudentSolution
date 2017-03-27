package com.server.data.structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.model.Gender;
import com.model.Student;
import com.model.Type;

public class StudentDataHolder {

	private Map<Long, Student> students;
	private StudentIndexByName studentsIndexedByName;
	private StudentCatalogs<Gender> studentsCatalogedByGender;
	private StudentCatalogs<Type> studentsCatalogedByType;
	
	public StudentDataHolder() {
		students = new HashMap<Long, Student>();
		studentsIndexedByName = new StudentIndexByName();
		studentsCatalogedByType = new StudentCatalogs<Type>();
		studentsCatalogedByGender = new StudentCatalogs<Gender>();
	}
	
	public void put(Student student){
		Long id = student.getId();
		students.put(id, student);
		
		studentsIndexedByName.put(student);
		
		Gender gender = student.getGender();
		studentsCatalogedByGender.put(student, gender);
		
		Type type = student.getType();
		studentsCatalogedByType.put(student, type);
	}
	
	public void update(Student student){
		studentsIndexedByName.update(student);
		
		Long id = student.getId();
		students.put(id, student);
		
		Gender gender = student.getGender();
		studentsCatalogedByGender.update(student, gender);
		
		Type type = student.getType();
		studentsCatalogedByType.update(student, type);
	}
	
	public void remove(Student student){
		studentsIndexedByName.remove(student);
		
		Long id = student.getId();
		students.remove(id);
		studentsCatalogedByGender.delete(id);
		studentsCatalogedByType.delete(id);
	}
	
	public boolean isEmpty(){
		return students.isEmpty();
	}
	
	public Student get(Long id){
		return students.get(id);
	}

	public Map<Long, Student> getStudents() {
		return students;
	}

	public void setStudents(Map<Long, Student> students) {
		this.students = students;
	}

	public StudentIndexByName getStudentsIndexedByName() {
		return studentsIndexedByName;
	}

	public void setStudentsIndexedByName(StudentIndexByName studentsIndexedByName) {
		this.studentsIndexedByName = studentsIndexedByName;
	}

	public StudentCatalogs<Gender> getStudentsCatalogedByGender() {
		return studentsCatalogedByGender;
	}

	public void setStudentsCatalogedByGender(
			StudentCatalogs<Gender> studentsCatalogedByGender) {
		this.studentsCatalogedByGender = studentsCatalogedByGender;
	}

	public StudentCatalogs<Type> getStudentsCatalogedByType() {
		return studentsCatalogedByType;
	}

	public void setStudentsCatalogedByType(
			StudentCatalogs<Type> studentsCatalogedByType) {
		this.studentsCatalogedByType = studentsCatalogedByType;
	}
	
	public Collection<Student> values(){
		return students.values();
	}
}
package com.server.data;

import java.util.Collection;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;

public interface DataService {

	public Student save(Student student);
	public void delete(Long id);
	public Student update(Student student);
	public boolean persist(String filename);
	public Map<String,Student> getStudentsByName();
	public Map<Gender,Collection<Student>> getStudentsByGender();
}

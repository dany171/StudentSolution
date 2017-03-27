package com.server.data;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.structures.StudentCatalogs;
import com.server.data.structures.StudentIndexByName;

public interface DataService {
	public Student save(Student student);
	public void delete(Long id);
	public Student update(Student student);
	public boolean persist(String filename);
	public boolean load(String filename);
	public StudentIndexByName getStudentsByName();
	public StudentCatalogs<Gender> getStudentsByGender();
	public StudentCatalogs<Type> getStudentsByType();
}

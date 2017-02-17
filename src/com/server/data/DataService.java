package com.server.data;

import java.util.Map;

import com.model.Student;

public interface DataService {

	public Student save(Student student);
	public void delete(Long id);
	public Student update(Student student);
	public Map<String,Student> getStudentsByName();
}

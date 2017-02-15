package com.server.data;

import com.model.Student;

public interface DataService {

	public void save(Student student);
	public void delete(Student student);
	public void update(Student student);
}

package com.server.data;

import com.model.Student;

public interface DataService {

	public void save(Student student);
	public void delete(Long id);
	public void update(Student student);
}

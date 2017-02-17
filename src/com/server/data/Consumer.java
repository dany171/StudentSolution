package com.server.data;

import com.model.Student;

public interface Consumer {
	
	public Student processSave(Student student);

	public void processDelete(Long id);

	public Student processUpdate(Student student);
}

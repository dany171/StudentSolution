package com.server.data;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public interface Consumer {
	public Student processSave(Student student);
	public void processDelete(Long id);
	public Student processUpdate(Student student);	
}

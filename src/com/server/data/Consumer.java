package com.server.data;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public interface Consumer {
	public void processSave(Student student);
	public void processDelete(Long id);
	public void processUpdate(Student student);	
}

package com.server.data;

import com.model.Student;

public class FileDataService implements DataService{

	@Override
	public void save(Student student) {
		System.out.println("user saved");
	}

	@Override
	public void delete(Student student) {
		System.out.println("user deleted");		
	}

	@Override
	public void update(Student student) {
		System.out.println("user updated");		
		
	}	
}

package com.server.search;

import java.util.Collection;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
import com.server.data.StudentIndexByName;
import com.server.data.StudentCatalogs;

/**
 * This perform searching and sorting tasks
 * 
 * @author Daniel Echalar
 *
 */
public interface StudentSearchService {

	public Collection<Student> search(Criteria criteria, DataService dataService);
	
}
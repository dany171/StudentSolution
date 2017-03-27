package com.server.search;

import java.util.Collection;

import com.model.Student;
import com.server.data.DataService;

/**
 * This perform searching and sorting tasks
 * 
 * @author Daniel Echalar
 *
 */
public interface StudentSearchService {

	public Collection<Student> search(Criteria criteria, DataService dataService);
	
}
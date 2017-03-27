package com.server.data.structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.model.Student;
/**
 * The objective of this is to avoid searching time by cataloging 
 * the students based on student's properties.
 * 
 * To do that we put all students with a common property in a catalog (represented by a map)
 * and hold that catalog in a map with the common property as key (catalog key).
 * 
 * That way keys of father map will contain student properties like MALE
 * , FEMALE, KINDERGARDEN, ELEMENTARY, HIGHSCHOOL and UNIVERSITY that will represent 
 * the catalogs 
 * 
 * And the map values will contain students 
 * 
 * @author Daniel Echalar
 *
 * @param <T> a property type that will be use as key to catalog a set of students
 */
public class StudentCatalogs<T> {
	
	// PROPERTIES
	Map<T, Map<Long, Student>> catalogs = new HashMap<T, Map<Long, Student>>();

	// METHODS
	/**
	 * Puts a student in a catalog defined by Property 
	 * 
	 * @param s a student to add
	 * @param catalogBy the catalog key where to put the student.
	 * if this doesn't exists then a new catalog is created it
	 */
	public void put(Student s, T catalogBy) {

		Map<Long, Student> catalog = catalogs.get(catalogBy);

		if (catalog == null) {
			catalog = new HashMap<Long, Student>();
			catalogs.put(catalogBy, catalog);
		}

		catalog.put(s.getId(), s);
	}

	/**
	 * Replaces a student for a new one. 
	 * 
	 * @param s the new student
	 * @param catalogBy the catalog key where to put the student.
	 * if this doesn't exists then a new catalog is created it
	 */
	public void update(Student s, T catalogBy) {

		for (Map<Long, Student> catalog : catalogs.values()) { // we do this as type could be changed
			
			Student oldStudent = catalog.get(s.getId());

			if (oldStudent != null) {
				catalog.remove(oldStudent.getId());
			}
		}

		put(s,catalogBy);
	}

	/**
	 * Removes a student from all catalogs
	 * 
	 * @param id the student id
	 */
	public void delete(Long id) {
		
		for (Map<Long, Student> map : catalogs.values()) {
			
			Student student = map.get(id);

			if (student != null) {
				map.remove(student.getId());
			}
		}
	}
	
	/**
	 * Get a catalog of the provided type
	 * 
	 * @param t the catalog type
	 * @return a catalog of students of provided type
	 * or null if catalog doesn't exists
	 */
	public Collection<Student> getCatalog(T t) {
		return catalogs.get(t).values();
	}
}
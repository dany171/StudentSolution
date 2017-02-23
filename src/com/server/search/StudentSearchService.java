package com.server.search;

import java.util.Collection;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.StudentIndexByName;
import com.server.data.StudentCatalogs;

/**
 * This perform searching and sorting tasks
 * 
 * @author Daniel Echalar
 *
 */
public interface StudentSearchService {

	/**
	 * Search students with provided name.
	 * Uses a names inverted index to perform the
	 * operation
	 * 
	 * @param name a string to look for
	 * @param studentsByName an inverted index of names
	 * @return a collection of students
	 */
	public Collection<Student> searchByName(String name,
			StudentIndexByName studentsByName);

	/**
	 * Search students by type.
	 * 
	 * Uses a catalog of students by type 
	 * to perform the operation 
	 * 
	 * @param type the type of students to look for
	 * @param studentsByType the catalogs of students where to look for 
	 * @return a collection of students of that type
	 */
	public Collection<Student> searchByType(Type type,
			StudentCatalogs<Type> studentsByType);

	/**
	 * Search students by gender
	 * 
	 * @param gender the gender of students to look for
	 * @param studentsByGender the catalogs of students where to look for 
	 * @return a collection of students of that gender
	 */
	public Collection<Student> searchByGender(Gender gender,
			StudentCatalogs<Gender> studentsByGender);

	/**
	 * A mix of searchByType and searchByGender
	 * 
	 * @param type the type of students to look for
	 * @param gender the gender of students to look for
	 * @param studentsByType catalogs of genders
	 * @param studentsByGender catalogs of types
	 * @return a collection of students defined that gender and type
	 */
	public Collection<Student> searchByTypeAndGender(Type type, Gender gender,
			StudentCatalogs<Type> studentsByType,
			StudentCatalogs<Gender> studentsByGender);
}
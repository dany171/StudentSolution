package com.server.data;

import com.model.Gender;
import com.model.Student;
import com.model.Type;

public interface DataService {

	/**
	 * Saves the student in server memory
	 * @param student the student to save
	 * @return the saved student. This comes with assigned id
	 */
	public Student save(Student student);

	/**
	 * Deletes the corresponding student
	 * 
	 * @param id the student id
	 */
	public void delete(Long id);

	/**
	 * Updates a student.
	 * 
	 * @param student the student to update
	 * @return the updated student
	 */
	public Student update(Student student);

	/**
	 * Writes all students into defined filename
	 *  
	 * @param filename
	 * @return
	 */
	public boolean persist(String filename);

	/**
	 * Load students from defined filename if exists
	 * 
	 * @param filename
	 * @return
	 */
	public boolean load(String filename);

	/**
	 * Get all student indexes by name
	 * 
	 * @return a inverted index containing student names and students
	 */
	public StudentsIndexByName getStudentsByName();

	/**
	 * Get catalogs of students by gender
	 * 
	 * @return catalogs of students by gender
	 */
	public StudentCatalogs<Gender> getStudentsByGender();

	/**
	 * get catalogs of students by type
	 * 
	 * @return catalogs of students by type
	 */
	public StudentCatalogs<Type> getStudentsByType();
}

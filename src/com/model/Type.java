package com.model;

import com.model.Type;

/**
 * Represents the types of students.
 * 
 * Types are:
 *  KINDERGARDEN
 *	ELEMENTARY
 *  HIGHSCHOOL
 *  UNIVERSITY
 * 
 * @author Daniel Echalar
 *
 */
public enum Type {

	KINDERGARDEN, ELEMENTARY, HIGHSCHOOL, UNIVERSITY;

	/**
	 * Provides a Type instance given a string 
	 * that is a valid gender keyword
	 * 
	 * Keywords are KINDERGARDEN, ELEMENTARY, HIGHSCHOOL, UNIVERSITY.
	 * Case insensitive
	 * 
	 * Or null if the string doesn't match any gender keyword
	 * 
	 * @param type a string containing a Type keyword
	 * @return a Type instance if string is a Type keyword. Else null.
	 */
	public static Type getType(String type) {

		if ("kindergarden".equalsIgnoreCase(type)) {
			return KINDERGARDEN;
		}

		if ("highschool".equalsIgnoreCase(type)) {
			return HIGHSCHOOL;
		}

		if ("university".equalsIgnoreCase(type)) {
			return UNIVERSITY;
		}

		if ("elementary".equalsIgnoreCase(type)) {
			return ELEMENTARY;
		}
		return null;
	}
}

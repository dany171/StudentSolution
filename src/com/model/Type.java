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

		if (type.equalsIgnoreCase("kindergarden")) {
			return KINDERGARDEN;
		}

		if (type.equalsIgnoreCase("highschool")) {
			return HIGHSCHOOL;
		}

		if (type.equalsIgnoreCase("university")) {
			return UNIVERSITY;
		}

		if (type.equalsIgnoreCase("elementary")) {
			return ELEMENTARY;
		}

		return null;
	}
}

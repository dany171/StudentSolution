package com.model;

import com.model.Gender;

/**
 * Represents the gender a student can be.
 * 
 * @author Daniel Echalar
 * 
 */
public enum Gender {

	MALE, FEMALE;

	/**
	 * Provides a Gender instance given a string 
	 * that is a valid gender keyword
	 * 
	 * Keywords are "male", "m", "female" and "m". Case insensitive
	 * 
	 * Or null if the string doesn't match any gender keyword
	 * 
	 * @param gender a string containing a Gender keyword
	 * @return a Gender instance if string is a gender keyword. Else null
	 */
	public static Gender getGender(String gender) {

		if (gender.equalsIgnoreCase("male") 
				|| gender.equalsIgnoreCase("m")) {
			
			return MALE;
		}

		if (gender.equalsIgnoreCase("female")
				|| gender.equalsIgnoreCase("f")) {
			return FEMALE;
		}

		return null;
	}

	@Override
	public String toString() {
		if (this.equals(MALE)) {
			return "M";
		} else {
			return "F";
		}
	}
}
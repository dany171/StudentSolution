package com.model;

import com.model.Gender;

public enum Gender {

	MALE, FEMALE;

	public static Gender getGender(String gender) {

		if ("male".equalsIgnoreCase(gender) 
				|| "m".equalsIgnoreCase(gender)) {
			return MALE;
		}
		if ("female".equalsIgnoreCase(gender)
				|| "f".equalsIgnoreCase(gender)) {
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
package com.model;

import com.model.Type;

public enum Type {

	KINDERGARDEN, ELEMENTARY, HIGHSCHOOL, UNIVERSITY;

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

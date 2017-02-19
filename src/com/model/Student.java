package com.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Comparable<Student>{
	
	private Long id;
	
	private String name;
	
	private Gender gender;
	
	private Type type;
	
	private Long timestamp;

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public enum Gender {
	
		MALE, FEMALE;

		public static Gender getGender(String gender) {
		
			if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("m")) { return MALE; }
			
			if (gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("f")) { return FEMALE; }
			
			return null;
		}

		public String toString() {
			
			if (this.equals(MALE)) { return "M"; } else { return "F"; }

		}
	}

	public enum Type {
		
		KINDERGARDEN, ELEMENTARY, HIGHSCHOOL, UNIVERSITY;

		public static Type getType(String type) {
			
			if (type.equalsIgnoreCase("kindergarden")) { return KINDERGARDEN; }
			
			if (type.equalsIgnoreCase("highschool")) { return HIGHSCHOOL; }
			
			if (type.equalsIgnoreCase("university")) { return UNIVERSITY; }
			
			if (type.equalsIgnoreCase("elementary")) { return ELEMENTARY; }
			
			return null;
		}
	}
	

	@Override
	public int compareTo(Student o) {
		return o.getTimestamp().compareTo(this.timestamp);
	}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public Gender getGender() { return gender; }

	public void setGender(Gender gender) { this.gender = gender; }

	public Type getType() {	return type; }

	public void setType(Type type) { this.type = type; }

	public Long getTimestamp() { return timestamp; }

	public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String toString() {
		Date ts = new Date(timestamp);
		
		return id.toString() + "-"
				+ type.toString() + "-" 
				+ name + "-"
				+ gender.toString() + "-"
				+ Student.dateFormatter.format(ts)+"@";
	}
}

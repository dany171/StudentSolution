package com.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an student.
 * 
 * @author Daniel Echalar
 * 
 */
public class Student implements Comparable<Student> {

	// STATICS
	private static DateFormat dateFormatter = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	// PROPERTIES
	private Long id;

	private String name;

	private Gender gender;

	private Type type;

	private Long timestamp;

	// METHODS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Student o) {
		return o.getTimestamp().compareTo(this.timestamp);
	}

	@Override
	public String toString() {
		
		Date ts = new Date(timestamp);
		String formattedTs = Student.dateFormatter.format(ts);
		
		return id.toString() + "-" +
			   type + "-" + 
		       name + "-" + 
			   gender + "-" +
			   formattedTs + "@";
	}
}

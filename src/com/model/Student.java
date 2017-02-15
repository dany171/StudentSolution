package com.model;

public class Student {
	private String name;
	private Gender gender;
	private Type type;
	private Long timestamp;	
	
	public enum Gender {
		MALE, FEMALE;
		
		public static Gender getGender(String gender){
			if(gender=="male"){
				return MALE;
			}
			if(gender=="female"){
				return FEMALE;
			}
			return null;
		}
	}
	
	public enum Type{
		KINDERGARDEN, HIGHSCHOOL, UNIVERTITY;
		
		public static Type getType(String type){
			if(type=="kindergarden"){
				return KINDERGARDEN;
			}
			if(type=="HIGHSCHOOL"){
				return HIGHSCHOOL;
			}
			if(type=="univertity"){
				return UNIVERTITY;
			}
			return null;
		}
	}

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
}

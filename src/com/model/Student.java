package com.model;

public class Student {
	private Long id;
	private String name;
	private Gender gender;
	private Type type;
	private Long timestamp;	
	
	public enum Gender {
		MALE, FEMALE;
		
		public static Gender getGender(String gender){
			if(gender.equalsIgnoreCase("male")){
				return MALE;
			}
			if(gender.equalsIgnoreCase("female")){
				return FEMALE;
			}
			return null;
		}
		
		public String toString(){
			if(this.equals(MALE)){
				return "M";
			}else{
				return "F";
			}
			
		}
	}
	
	public enum Type{
		KINDERGARDEN, HIGHSCHOOL, UNIVERSITY;
		
		public static Type getType(String type){
			if(type.equalsIgnoreCase("kindergarden")){
				return KINDERGARDEN;
			}
			if(type.equalsIgnoreCase("highschool")){
				return HIGHSCHOOL;
			}
			if(type.equalsIgnoreCase("university")){
				return UNIVERSITY;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString(){
		return id.toString()+", "+type.toString()+", "+name+", "+gender.toString()+", "+timestamp.toString();
	}
}

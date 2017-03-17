package com.server.search;

import com.model.Gender;
import com.model.Type;

public class Criteria {
    
	final private String name;
    final private Type type;
    final private Gender gender;
    
    public Criteria(String name, Type type, Gender gender){
    	this.name = name;
    	this.type = type;
    	this.gender = gender;
    }
    
	public String getName() {
		return name;
	}
	public Type getType() {
		return type;
	}
	public Gender getGender() {
		return gender;
	}
	
	public boolean hasName(){
		return name!=null;
	}
	
	public boolean hasType(){
		return type!=null;
	}
	
	public boolean hasGender(){
		return gender!=null;
	}
}

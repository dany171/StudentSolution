package com.server.data;

import java.util.TreeMap;

import com.model.Student;

public class InvertedIndexTree {
/*
	public TreeMap<String,TreeMap<Long,Student>> index;
	
	public InvertedIndexTree(){
		index = new TreeMap<String,TreeMap<Long,Student>>();
	}
	
	public void put(Student student){
		String name = student.getName();
		
		for(int i=1; i<name.length(); i++){
			String sub = name.substring(0,i);
			
			TreeMap<Long,Student> indexValue = index.get(sub);
			
			if(indexValue==null){
				indexValue = new TreeMap<Long,Student>();
			}
			
			indexValue.put(student.getId(), student);
			
			index.put(sub, indexValue);
		}
	}
	
	public TreeMap<Long,Student> get(String name){
		
	}*/
}

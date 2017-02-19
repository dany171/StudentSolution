package com.server.data;

import java.util.Collection;
import java.util.TreeMap;

import com.model.Student;


public class InvertedIndexTreeByName {

	public TreeMap<String,TreeMap<Long,Student>> index;
	
	public InvertedIndexTreeByName(){
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
		
		Collection<String> indexes = index.keySet();
		
		TreeMap<Long,Student> res = new TreeMap<Long,Student>();
		
		for(String key : indexes){
			if(key.contains(name) || key==name){
				
				res.putAll(index.get(key));
			}
		}
		
		return res;
	}
	
	public void update(Student student){
		remove(student);
		put(student);
	}
	
	public void remove(Student student){
		
		String name = student.getName();
		
		for(int i=1; i<name.length(); i++){
			String sub = name.substring(0,i);
			
			TreeMap<Long,Student> indexValue = index.get(sub);
			
			if(indexValue!=null){
				indexValue.remove(student.getId());
			}
		}
	}
}

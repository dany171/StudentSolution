package com.server.data.structures;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.model.Student;

/**
 * The objective of this is to reduce searching time by creating
 * a inverted index of students by names
 * 
 * This allows to get all students where names contain
 * a provided text.
 * 
 * @author Daniel Echalar
 *
 */
public class StudentIndexByName {

	public TreeMap<String,TreeMap<Long,Student>> index;
	
	public StudentIndexByName(){
		index = new TreeMap<String,TreeMap<Long,Student>>();
	}
	
	/**
	 * Puts a student in index
	 * 
	 * substrings of student name are added as indexes
	 * 
	 * Eg: name = daniel
	 * indexes: d , da , dan , dani , danie , daniel
	 * 
	 * @param student the student to put
	 */
	public void put(Student student){
		String name = student.getName();
		
		for(int i=1; i<=name.length(); i++){
			String sub = name.substring(0,i);
			TreeMap<Long,Student> indexValue = index.get(sub);
			
			if(indexValue==null){
				indexValue = new TreeMap<Long,Student>();
			}
			indexValue.put(student.getId(), student);
			index.put(sub, indexValue);
			
		}
	}
	
	/**
	 * Gets a map of students containing the name or
	 * at least part of the name
	 * 
	 * @param name the name to look for
	 * @return a map of students with IDs as keys
	 * the map is empty if no students found with that name
	 */
	public Map<Long,Student> get(String name){
		Collection<String> indexes = index.keySet();
		TreeMap<Long,Student> res = new TreeMap<Long,Student>();
		
		for(String key : indexes){
			if(key.contains(name)){
				res.putAll(index.get(key));
			}
		}
		
		return res;
	}
	
	/**
	 * Updates a student in index
	 * 
	 * First removes it from all indexes (looking by Id)
	 * then adds the new student
	 * 
	 * @param student the student to update
	 */
	public void update(Student student){
		remove(student);
		put(student);
	}
	
	/**
	 * Removes a student from indexes
	 * 
	 * @param student the student to remove
	 */
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
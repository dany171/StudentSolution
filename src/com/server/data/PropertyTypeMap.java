package com.server.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.model.Student;

public class PropertyTypeMap<T> {

	Map<T,Map<Long,Student>> propertyTypesColl = new HashMap<T,Map<Long,Student>>();
	
	public void put(Student s,T propertyType){
		
		Map<Long,Student> destinationMap = propertyTypesColl.get(propertyType);
		
		if(destinationMap==null){
			destinationMap = new HashMap<Long,Student>();
			propertyTypesColl.put(propertyType, destinationMap);
		}
		
		destinationMap.put(s.getId(),s);
		
	}
	
	public void update(Student s,T propertyType){
		
		for(Map<Long,Student> map : propertyTypesColl.values()){//we do this as type could be changed
			Student oldStudent = map.get(s.getId());
			
			if(oldStudent!=null){
				map.remove(oldStudent.getId());
			}
		}
		
		//then we add it to the destination map
		Map<Long,Student> destinationMap = propertyTypesColl.get(propertyType);
		
		if(destinationMap==null){
			destinationMap = new HashMap<Long,Student>();
			propertyTypesColl.put(propertyType, destinationMap);
		}
		
		destinationMap.put(s.getId(),s);		
		
	}
	
	public void delete(Long id){
		for(Map<Long,Student> map : propertyTypesColl.values()){
			Student student = map.get(id);
			
			if(student!=null){
				map.remove(student.getId());
			}
		}
	}	
	
	public Collection<Student> getPropertyCollection(T t){
		return propertyTypesColl.get(t).values();
	}
	
}

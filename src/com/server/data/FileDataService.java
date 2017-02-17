package com.server.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public class FileDataService implements DataService{

	Long lastId = 1L;
	
	Map<Long, Student> students;
	
	Map<String, Student> byName;
	
	PropertyTypeMap<Gender> byGender;
	
	PropertyTypeMap<Type> byType;
	
	

	public FileDataService(){
		students = new HashMap<Long,Student>();
		byName = new HashMap<String,Student>();
				
		byType = new PropertyTypeMap<Type>();
		byGender= new PropertyTypeMap<Gender>();		
	}
	
	@Override
	public Student save(Student student) {
		if(students.isEmpty()){
			student.setId(lastId);
		}else{
			lastId++;
			student.setId(lastId);
		}
		Date d = new Date();
		
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(),student);
		
		byName.put(student.getName(),student);
		
		byGender.put(student, student.getGender());	
				
		byType.put(student, student.getType());
		
		System.out.println("user saved");
		
		return student;		
	}

	@Override
	public Student update(Student student) {
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(), student);
		byName.put(student.getName(),student);
		
		//gender
		byGender.update(student, student.getGender());
		
		//type
		byType.update(student, student.getType());
		
		System.out.println("user updated");		
		return student;
	}

	@Override
	public void delete(Long id) {
		
		Student student = students.get(id);

		byName.remove(student.getName());
		
		students.remove(id);
				
		byGender.delete(student.getId());	

		byType.delete(student.getId());
		
		System.out.println("User deleted");
	}	
	
	private Map<String,Student> createStudentsByName(Map<Long, Student> original){
		
		Map<String,Student> byName = new HashMap<String,Student>(original.size());
		Collection<Student> students = original.values();
		Iterator<Student> i = students.iterator();
		
		while(i.hasNext()){
			Student student = i.next();
			byName.put(student.getName(), student);
		}
		return byName;		
	}
	
	public Map<String, Student> getStudentsByName(){		
		return byName;		
	}
	
	public PropertyTypeMap<Gender> getStudentsByGender(){
		return byGender;
	}
	
	public PropertyTypeMap<Type> getStudentsByType() {
		return byType;
	}

	public void setStudentsByType(PropertyTypeMap<Type> byType) {
		this.byType = byType;
	}

	@Override
	public boolean persist(String filename) {
		PrintWriter writer =null;
		boolean res = false;
		try{
			Iterator<Student> i = students.values().iterator();
			writer = new PrintWriter(filename, "UTF-8");
			while(i.hasNext()){
				
			    writer.println(i.next().toString());
			    
			}
			writer.close();
			res = true;
		} catch (IOException e) {
		   System.out.println(e);
		   if(writer!=null){
			   writer.close();
		   }
		   res = false;
		}
		return res;
	}

	@Override
	public boolean load(String filename) {
		    String line = "";
	        String cvsSplitBy = ",";
	        boolean res = false;
	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] studentStr = line.split(cvsSplitBy);
	                Long id = new Long(studentStr[0]);
	                Student.Type type = Student.Type.getType(studentStr[1]);
	                String name = studentStr[2];
	                Student.Gender gender = Student.Gender.getGender(studentStr[3]);
	                Long timestamp = new Long(studentStr[4]);
	                
	                Student student = new Student();
	                student.setId(id);
	                student.setType(type);
	                student.setName(name);
	                student.setGender(gender);
	                student.setTimestamp(timestamp);

	                students.put(student.getId(),student);

	                byName.put(student.getName(),student);
	        		
	        		byGender.put(student, student.getGender());	
	        		
	        		byType.put(student, student.getType());
	        		
	        		if(id>lastId){ lastId = id; }
	        		
	        		res = true;
	            }

	        } catch (IOException e) {
	            return false;
	        }
	        return res;
	}	
}

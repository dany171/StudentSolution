package com.server.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;

public class FileDataService implements DataService {

	Long lastId = 1L;

	Map<Long, Student> students;

	Map<String, Student> byName;

	InvertedIndexTreeByName iByName;
	
	PropertyTypeMap<Gender> byGender;

	PropertyTypeMap<Type> byType;

	public FileDataService() {
		
		students = new HashMap<Long, Student>();
		
		byName = new HashMap<String, Student>();
		
		iByName = new InvertedIndexTreeByName();

		byType = new PropertyTypeMap<Type>();
		
		byGender = new PropertyTypeMap<Gender>();
	}

	@Override
	public synchronized Student save(Student student) {
		
		if (students.isEmpty()) {
			
			student.setId(lastId);
			
		} else {
			
			lastId++;
			
			student.setId(lastId);
			
		}
		
		Date d = new Date();

		student.setTimestamp(d.getTime());

		students.put(student.getId(), student);

		byName.put(student.getName(), student);
		
		iByName.put(student);

		byGender.put(student, student.getGender());

		byType.put(student, student.getType());

		System.out.println("user saved");

		return student;
	}

	@Override
	public synchronized Student update(Student student) {
		
		Date d = new Date();
		
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(), student);
		
		byName.put(student.getName(), student);
		
		iByName.update(student);
		
		byGender.update(student, student.getGender());
		
		byType.update(student, student.getType());

		System.out.println("user updated");

		return student;
	}

	@Override
	public synchronized void delete(Long id) {

		Student student = students.get(id);

		byName.remove(student.getName());
		
		iByName.remove(student);

		students.remove(id);

		byGender.delete(student.getId());

		byType.delete(student.getId());

		System.out.println("User deleted");
	}

	//public Map<String, Student> getStudentsByName() { return byName; }

	//public Map<String, Student> getStudentsByName() { return byName; }
	
	public InvertedIndexTreeByName getStudentsByName() { return iByName; }
	
	public PropertyTypeMap<Gender> getStudentsByGender() { return byGender;	}

	public PropertyTypeMap<Type> getStudentsByType() { return byType; }

	public void setStudentsByType(PropertyTypeMap<Type> byType) { this.byType = byType;	}

	@Override
	public synchronized boolean persist(String filename) {
		
		PrintWriter writer = null;
		
		boolean res = false;
		
		try {
			
			Iterator<Student> i = students.values().iterator();
			
			writer = new PrintWriter(filename, "UTF-8");
			
			while (i.hasNext()) {

				writer.println(i.next().toString());

			}
			
			res = true;
			
		} catch (IOException e) {
			
			System.out.println(e);
			
			res = false;
			
		}finally{
			if (writer != null) {
				
				writer.close();
			}
		}
		return res;
	}

	@Override
	public synchronized boolean load(String filename) {
		
		String line = "";
		
		String splitBy = "-";
		
		boolean res = false;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

			while ((line = br.readLine()) != null) {
				
				line = line.replaceAll("@", "");
				
				String[] studentStr = line.split(splitBy);
				
				Long id = new Long(studentStr[0]);
				
				Student.Type type = Student.Type.getType(studentStr[1]);
				
				String name = studentStr[2].trim();
				
				Student.Gender gender = Student.Gender.getGender(studentStr[3]);
				
				DateFormat a = new SimpleDateFormat("yyyyMMddHHmmss");
				
				Long timestamp=null;
				
				try {
					Date d = a.parse(studentStr[4].trim());
					timestamp = d.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Student student = new Student();
				
				student.setId(id);
				
				student.setType(type);
				
				student.setName(name);
				
				student.setGender(gender);
				
				student.setTimestamp(timestamp);

				students.put(student.getId(), student);

				byName.put(student.getName(), student);
				
				iByName.put(student);

				byGender.put(student, student.getGender());

				byType.put(student, student.getType());

				if (id > lastId) {//this could be improved with another approach
					
					lastId = id;
					
				}

				res = true;
			}

		} catch (IOException e) {
			return false;
		}
		return res;
	}
}
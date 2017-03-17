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

import com.model.Gender;
import com.model.Student;
import com.model.Type;
/**
 * Handles data and persistence
 * 
 * @author Daniel Echalar
 *
 */
public class FileDataService implements DataService {

	// PROPERTIES
	Long lastId = 1L;
	
	Map<Long, Student> students;

	StudentIndexByName studentsIndexedByName;
	
	StudentCatalogs<Gender> studentsCatalogedByGender;

	StudentCatalogs<Type> studentsCatalogedByType;

	
	// CONSTRUCTOR
	public FileDataService() {
		students = new HashMap<Long, Student>();
		studentsIndexedByName = new StudentIndexByName();
		studentsCatalogedByType = new StudentCatalogs<Type>();
		studentsCatalogedByGender = new StudentCatalogs<Gender>();
	}

	@Override
	public synchronized Student save(Student student) {

		// set student id
		if (students.isEmpty()) {
			student.setId(lastId);
		} else {
			lastId++;
			student.setId(lastId);
		}
		
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(), student);
		studentsIndexedByName.put(student);
		studentsCatalogedByGender.put(student, student.getGender());
		studentsCatalogedByType.put(student, student.getType());

		System.out.println("user saved");
		return student;
	}

	@Override
	public synchronized Student update(Student student) {
		
		Date d = new Date();
		student.setTimestamp(d.getTime());
		
		students.put(student.getId(), student);
		studentsIndexedByName.update(student);
		studentsCatalogedByGender.update(student, student.getGender());
		studentsCatalogedByType.update(student, student.getType());

		System.out.println("user updated");
		return student;
	}

	@Override
	public synchronized void delete(Long id) {

		Student student = students.get(id);

		studentsIndexedByName.remove(student);
		students.remove(id);
		studentsCatalogedByGender.delete(student.getId());
		studentsCatalogedByType.delete(student.getId());

		System.out.println("User deleted");
	}
		
	public StudentIndexByName getStudentsByName() { return studentsIndexedByName; }
	
	public StudentCatalogs<Gender> getStudentsByGender() { return studentsCatalogedByGender;	}

	public StudentCatalogs<Type> getStudentsByType() { return studentsCatalogedByType; }

	public void setStudentsByType(StudentCatalogs<Type> byType) { this.studentsCatalogedByType = byType;	}

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
				Type type = Type.getType(studentStr[1]);
				String name = studentStr[2].trim();
				Gender gender = Gender.getGender(studentStr[3]);
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
				studentsIndexedByName.put(student);
				studentsCatalogedByGender.put(student, student.getGender());
				studentsCatalogedByType.put(student, student.getType());

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
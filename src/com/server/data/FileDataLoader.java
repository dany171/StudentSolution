package com.server.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.structures.StudentDataHolder;

public class FileDataLoader {
	
	final String splitBy = "-";
	final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public synchronized boolean load(String filename, StudentDataHolder students) {
		
		boolean res = false;
		String line = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

			while ((line = br.readLine()) != null) {
				line = line.replaceAll("@", "");
				String[] studentStr = line.split(splitBy);

				Long id = new Long(studentStr[0]);
				Type type = Type.getType(studentStr[1]);
				String name = studentStr[2].trim();
				Gender gender = Gender.getGender(studentStr[3]);

				Long timestamp = null;

				try {
					Date d = dateFormatter.parse(studentStr[4].trim());
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

				students.put(student);
				res = true;
			}

		} catch (IOException e) {
			return false;
		}
		return res;
	}
}

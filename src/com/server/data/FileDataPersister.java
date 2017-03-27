package com.server.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import com.model.Student;

public class FileDataPersister {

	public synchronized boolean persist(String filename, Collection<Student> students) {
		
		PrintWriter writer = null;
		boolean res = false;
		
		try {
			Iterator<Student> i = students.iterator();
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
}

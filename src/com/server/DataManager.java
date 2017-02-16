package com.server;

import com.exceptions.BadDeleteException;
import com.exceptions.BadStudentException;
import com.model.Student;
import com.search.StudentSearchService;
import com.server.data.Consumer;
import com.server.data.DataService;

public class DataManager implements Consumer{

	private DataService dataService;
	private StudentSearchService searchService;
	
	public DataManager(DataService dataService){
		this.dataService = dataService;		
	}
	
	public String query(String query){
		
		String[] words = query.split(" ");
		Student student = null;
		
		String res = "end of query";
		
		switch(words[0]){
			case "create":
				try {
					student = createUser(query);
					processSave(student);
					res = "Student Created";
				} catch (BadStudentException e) {
					return e.toString()+": student details incorrect";
				}				
				break;
			case "delete":
				try{
					Long userId = deleteUserParser(query);
					processDelete(userId);
					res = "Student deleted";
				}catch(BadDeleteException e){
					return e.toString()+": bad delete. No student deleted";
				}
				
				break;
			case "update":
				/*student = createUser(query);
				dataService.update(student);*/
				break;
			default:
				System.out.println("wrong command");
				break;
		}
		return res;
	}
	
	public Student createUser(String text) throws BadStudentException{
		int startName = text.indexOf("name=")+5;
		int endName = text.indexOf("gender=",startName+1>=text.length()? text.length()-1:startName+1);
		String name = text.substring(startName, endName);
		name = name.trim();		
						
		int startGender = text.indexOf("gender=")+7;
		int endGender = text.indexOf("type=",startGender+1>=text.length()? text.length()-1:startGender+1);
		String gender = text.substring(startGender, endGender);
		gender = gender.trim();
		
		int startType = text.indexOf("type=")+5;
		int endType = text.length();
		String type = text.substring(startType, endType);
		type = type.trim();
		
		Student student = new Student();
		student.setName(name);
		student.setGender(Student.Gender.getGender(gender));
		student.setType(Student.Type.getType(type));
		
		if(student.getName().equalsIgnoreCase("") ||
		   student.getGender()==null ||
		   student.getType()==null
		){ 
			throw new BadStudentException();
		};
		
		return student;
		
	}	
	
	private Long deleteUserParser(String text) throws BadDeleteException{
		try{	
			String[] words = text.split(" ");
			return new Long(words[1]);
		}catch(Exception e){
			throw new BadDeleteException();
		}
	}

	@Override
	public void processSave(Student student) {
		dataService.save(student);		
	}
	
	@Override
	public void processDelete(Long id) {
		dataService.delete(id);		
	}

	@Override
	public void processUpdate(Student student) {
		dataService.update(student);
	}

	public void setStudentSearchService(StudentSearchService sss){
		this.searchService = sss;
	}

/*	@Override
	public void processSearchByName(String name) {
		searchService.searchByName(name);
		
	}

	@Override
	public void processSearchByType(Type type) {
        searchService.searchByType(type);
		
	}

	@Override
	public void processSearhByTypeAndGender(Type type, Gender gender) {
		searchService.searchByTypeAndGender(type, gender);		
	}*/
}

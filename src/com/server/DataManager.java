package com.server;

import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;
import com.search.SearchConsumer;
import com.search.StudentSearchService;
import com.server.data.Consumer;
import com.server.data.DataService;

public class DataManager implements Consumer, SearchConsumer{

	private DataService dataService;
	private StudentSearchHandler searchService;NO USAR INJECTOR, DIRECTAMENTE UN OBJETO QUE IMPLEMENTE STRATEGY
	
	public DataManager(DataService dataService){
		this.dataService = dataService;
		this.searchService = searchService;
	}
	
	public void query(String query){
		
		String[] words = query.split(" ");
		Student student = null;
		
		switch(words[0]){
			case "create":
				student = createUser(query);
				dataService.save(student);
				break;
			case "delete":
				student = createUser(query);
				dataService.delete(student);
				break;
			case "update":
				student = createUser(query);
				dataService.update(student);
				break;
			default:
				System.out.println("wrong command");
				break;
			}	
	}
	
	public Student createUser(String text){
		int startName = text.indexOf("name=")+5;
		int endName = text.indexOf("gender=",startName+1>=text.length()? text.length()-1:startName+1);
		String name = text.substring(startName, endName);
		
		int startGender = text.indexOf("gender=")+7;
		int endGender = text.indexOf("type=",startGender+1>=text.length()? text.length()-1:startGender+1);
		String gender = text.substring(startGender, endGender);
		
		int startType = text.indexOf("type=")+5;
		int endType = text.length()-1;
		String type = text.substring(startType, endType);
		
		Student student = new Student();
		student.setName(name);
		student.setGender(Student.Gender.getGender(gender));
		student.setType(Student.Type.getType(type));
		
		return student;
		
	}

	@Override
	public void processSave(Student student) {
		dataService.save(student);		
	}

	@Override
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
	}
}

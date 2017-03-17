package com.server;

import java.util.Collection;

import com.exceptions.BadRequestException;
import com.model.Student;
import com.server.data.DataService;
import com.server.data.FileDataServiceBuilder;
import com.server.data.IDataServiceBuilder;
import com.server.search.BasicStudentSearchServiceBuilder;
import com.server.search.IStudentSearchServiceBuilder;
import com.server.search.StudentSearchService;
import command.CreateCommand;
import command.DeleteCommand;
import command.EditCommand;
import command.Result;
import command.SearchCommand;

public class CommandManager{

	// CONSTANTS
	private final String FILENAME = "input.csv";

	// PROPERTIES
	private DataService dataService;
	private StudentSearchService studentSearchService;
	
	// CONSTRUCTOR
	public CommandManager() {
		
		IDataServiceBuilder dataServiceBuilder = new FileDataServiceBuilder();
		dataService = dataServiceBuilder.getResult();
		
		IStudentSearchServiceBuilder studentSearchServiceBuilder = new BasicStudentSearchServiceBuilder();   
		studentSearchService = studentSearchServiceBuilder.getResult();
		
		boolean dataLoaded = dataService.load(FILENAME);
		System.out.println("load data: " + dataLoaded);
	}

	// METHODS
	public String execute(String text) {

		String res = "";
		try {
			
			String cmd = getFirstWord(text);  
			
			switch(cmd){
			case "CREATE":
				CreateCommand createCommand = new CreateCommand(dataService, text);
				Result<Student> createResult = createCommand.execute();
				res = "created: "+createResult.toString();
				break;
			case "UPDATE":
				EditCommand editCommand = new EditCommand(dataService, text);
				Result<Student> editResult = editCommand.execute();
				res = "edited: "+editCommand.toString();
				break;
			case "DELETE":
				DeleteCommand deleteCommand = new DeleteCommand(dataService, text);
				Result<Boolean> deleteResult = deleteCommand.execute();
				res = "deleted: "+deleteResult.toString();
				break;
			case "SEARCH":
				SearchCommand searchCommand = new SearchCommand(studentSearchService, dataService, text);
				Result<Collection<Student>> searchResult = searchCommand.execute();
				res = "Results: "+searchResult.toString();
				break;
			case "EXIT":
				executeExit(true);
				break;
			default:
				res = "Command not found";
			}
			
			return res;
			
		} catch (BadRequestException be) {
			return be.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}		
	}

	
	public String executeExit(boolean persist) {

		if (persist) {
			
			boolean exitSuccess = processExit(FILENAME);

			if (exitSuccess) {
				return "All data persisted. bye";
			} else {
				return "Could not persist data. bye";
			}
			
		} else {
			return "Ok, we are not saving anything! :) bye";
		}
	}
	
	public boolean processExit(String filename) {
		return dataService.persist(filename);
	}
	
	public String getFirstWord(String text) throws BadRequestException{
		String[] words = text.split(" ");
		String res = words[0];
		res = res.toUpperCase();
		return res;
	}
}
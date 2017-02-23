package com.server;

import java.util.Collection;
import java.util.HashMap;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
import com.server.data.FileDataServiceBuilder;
import com.server.data.IDataServiceBuilder;
import com.server.data.StudentCatalogs;
import com.server.data.StudentIndexByName;
import com.server.search.BasicStudentSearchServiceBuilder;
import com.server.search.IStudentSearchServiceBuilder;
import com.server.search.StudentSearchService;

/**
 * Process commands
 * 
 * This has a DataService and a StudentSearchService 
 * to process commands.
 * 
 * Implements Consumer class so it gets DataService injected
 *  
 * Uses a DataService implementation
 * to load students contained in input.csv file 
 * 
 * @author Daniel Echalar
 *
 */
public class CommandExecutor{

	// CONSTANTS
	private final String FILENAME = "input.csv";

	// PROPERTIES
	private DataService dataService;
	
	private StudentSearchService studentSearchService;

	// CONSTRUCTOR
	public CommandExecutor() {
		
		IDataServiceBuilder dataServiceBuilder = new FileDataServiceBuilder();
		dataService = dataServiceBuilder.getResult();
		
		IStudentSearchServiceBuilder studentSearchServiceBuilder = new BasicStudentSearchServiceBuilder();   
		studentSearchService = studentSearchServiceBuilder.getResult();
		
		boolean dataLoaded = this.dataService.load(FILENAME);
		System.out.println("load data: " + dataLoaded);
	}

	// METHODS
	/**
	 * Parses a command and execute it.
	 * 
	 * @param command a string containing the command to execute
	 * @return a string containing the response to command execution
	 * @throws an exception if something fails
	 */
	public String execute(String command) {

		Command cmd;
		String res = "";
		
		try {
			cmd = parseCommand(command);
			if(cmd==null){
				return "Command not found";
			}
			switch(cmd){
				case CREATE:
					res = executeCreateStudent(cmd);
					break;
				case UPDATE:
					res = executeUpdateStudent(cmd);
					break;
				case DELETE:
					res = executeDeleteStudent(cmd);
					break;
				case SEARCH_BY_TYPE_AND_GENDER:
					res = executeSearchByTypeAndGender(cmd);
					break;
				case SEARCH_BY_NAME:
					res = executeSearchByName(cmd);
					break;
				case SEARCH_BY_GENDER:
					res = executeSearchByGender(cmd);
					break;
				case SEARCH_BY_TYPE:
					res = executeSearchByType(cmd);
					break;
				case EXIT:
					res = executeExit(cmd);
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

	/**
	 * Execute create student command.
	 * 
	 * example:
	 * create name=john gender=male type=kindergarden
	 * 
	 * @param cmd a string containing the command
	 * @return a string representation of created student
	 * @throws BadStudentException if error while saving the student or 
	 * if the command is wrong
	 */
	private String executeCreateStudent(Command cmd) throws BadRequestException {

		HashMap<String, String> options = cmd.getOptions();

		Student student = new Student();
		student.setName(options.get("name"));
		student.setGender(Gender.getGender(options.get("gender")));
		student.setType(Type.getType(options.get("type")));

		if (student.getName() == null 
				|| student.getName().equalsIgnoreCase("") 
				|| student.getGender() == null 
				|| student.getType() == null) {
			
			throw new BadRequestException("Missing name, gender or type");
		}

		try {
			Student newStudent = processSave(student);
			return "Student created:" + newStudent.toString();

		} catch (Exception e) {
			throw new BadRequestException("Error while saving", e);
		}
	}

	/**
	 * Execute update student command 
	 * 
	 * example
	 * update id=1 name=bob gender=male type=elementary
	 * 
	 * @param cmd a string containing the command
	 * @return a string representation of created student
	 * @throws BadUpdateException  if error while updating the student or 
	 * if the command is wrong
	 */
	private String executeUpdateStudent(Command cmd) throws BadRequestException {
		
		HashMap<String, String> options = cmd.getOptions();

		Student student = new Student();
		student.setId(new Long(options.get("id")));
		student.setName(options.get("name"));
		student.setGender(Gender.getGender(options.get("gender")));
		student.setType(Type.getType(options.get("type")));

		if (student.getId() == null
				|| student.getName() == null
				|| student.getName().equalsIgnoreCase("")
				|| student.getGender() == null
				|| student.getType() == null) {
			
			throw new BadRequestException("Missing id, name, gender or type");
		}
		
		try {
			Student updatedStudent = processUpdate(student);
			return "Student updated:" + updatedStudent.toString();

		} catch (Exception e) {
			throw new BadRequestException("Error while updating", e);
		}
	}

	/**
	 * Execute delete student command 
	 * 
	 * example:
	 * delete 5
	 * 
	 * @param cmd a string containing the command
	 * @return a message pointing the ID of the deleted student
	 * @throws BadRequestException  if error while deleting the student or 
	 * if the command is wrong
	 */
	private String executeDeleteStudent(Command cmd) throws BadRequestException {
		
		HashMap<String, String> options = cmd.getOptions();
		Long id;
		
		try {
			String strId = options.get("id");
			id = new Long(strId);
			
		} catch (Exception e) {			
			throw new BadRequestException("Id empty or incompatible", e);			
		}

		try {			
			processDelete(id);
			return "Student with ID: " + id + " was deleted";
			
		} catch (Exception e) {			
			throw new BadRequestException("Error while deleting", e);			
		}
	}

	/**
	 * Execute search by name command 
	 * 
	 * example:
	 * search name=john
	 * 
	 * @param cmd a string containing the command
	 * @return a string containing the results
	 * @throws BadSearchException  if error while searching students or 
	 * if the command is wrong
	 */
	private String executeSearchByName(Command cmd) throws BadRequestException {

		HashMap<String, String> options = cmd.getOptions();
		String name = options.get("name");

		if (name == null) {
			throw new BadRequestException("Missing name option");
		}

		try {
			Collection<Student> students = processSearchByName(name, 
					dataService.getStudentsByName());
			
			if (students == null || students.isEmpty()) {
				return "Student not found";
			} else {
				return students.toString();
			}

		} catch (Exception e) {
			throw new BadRequestException("Error while searching by name", e);
		}
	}

	/**
	 * Execute search by gender command 
	 * 
	 * example:
	 * search gender=male
	 * 
	 * @param cmd a string containing the command
	 * @return a string containing the results
	 * @throws BadSearchException  if error while searching students or 
	 * if the command is wrong
	 */
	private String executeSearchByGender(Command cmd) throws BadRequestException {

		HashMap<String, String> options = cmd.getOptions();
		String gender = options.get("gender");

		if (gender == null) {
			throw new BadRequestException("Missing gender option");
		}

		try {
			Collection<Student> students = processSearchByGender(
					Gender.getGender(gender), 
					dataService.getStudentsByGender());
			
			if (students == null || students.isEmpty()) {
				return "No students found";
			} else {
				return students.toString();
			}
			
		} catch (Exception e) {
			throw new BadRequestException("Error while searching by gender", e);
		}

	}

	/**
	 * Execute search by type command 
	 * 
	 * example:
	 * search type=kindergarden
	 * 
	 * @param cmd a string containing the command
	 * @return a string containing the results
	 * @throws BadSearchException  if error while searching students or 
	 * if the command is wrong
	 */
	private String executeSearchByType(Command cmd) throws BadRequestException {

		HashMap<String, String> options = cmd.getOptions();
		String type = options.get("type");

		if (type == null) {
			throw new BadRequestException("Missing type option");
		}

		try {
			Collection<Student> students = processSearchByType(
					Type.getType(type), 
					dataService.getStudentsByType());
			
			if (students == null || students.isEmpty()) {
				return "No students found";
			} else {
				return students.toString();
			}
			
		} catch (Exception e) {
			throw new BadRequestException("Error while searching by gender", e);
		}
	}

	/**
	 * Execute search by type  and gender command 
	 * 
	 * example:
	 * search type=kindergarden gender=male
	 * 
	 * @param cmd a string containing the command
	 * @return a string containing the results
	 * @throws BadSearchException  if error while searching students or 
	 * if the command is wrong
	 */
	private String executeSearchByTypeAndGender(Command cmd) throws BadRequestException {

		HashMap<String, String> options = cmd.getOptions();
		String type = options.get("type");
		String gender = options.get("gender");

		if (type == null) {
			throw new BadRequestException("Missing type option");
		}
		if (gender == null) {
			throw new BadRequestException("Missing gender option");
		}

		try {
			StudentCatalogs<Type> studentsByTypes = dataService.getStudentsByType();
			StudentCatalogs<Gender> studentsByGender = dataService.getStudentsByGender();
			
			Collection<Student> students = processSearchByTypeAndGender(
					Type.getType(type), 
					Gender.getGender(gender),
					studentsByTypes,
					studentsByGender);
			
			if (students == null || students.isEmpty()) {
				return "No students found";
			} else {
				return students.toString();
			}
			
		} catch (Exception e) {
			throw new BadRequestException("Error while searching by type and gender", e);
		}
	}

	/**
	 * Execute exit command.
	 * Persists all data before exiting
	 * 
	 * example:
	 * exit
	 * 
	 * @param cmd a string containing the command
	 * @return a message saying bye if everything went fine
	 * or, a error message if persistence failed
	 */
	public String executeExit(Command cmd) {

		HashMap<String, String> options = cmd.getOptions();
		boolean persist = new Boolean(options.get("persist"));

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

	public Student processSave(Student student) {
		return dataService.save(student);
	}

	public void processDelete(Long id) {
		dataService.delete(id);
	}

	public Student processUpdate(Student student) {
		return dataService.update(student);
	}

	public void setStudentSearchService(StudentSearchService sss) {
		this.studentSearchService = sss;
	}

	public Collection<Student> processSearchByName(
			String name, 
			StudentIndexByName studentsByName) {
		
		return studentSearchService.searchByName(name, studentsByName);
	}

	public Collection<Student> processSearchByGender(
			Gender gender,
			StudentCatalogs<Gender> studentsByGender) {
		
		return studentSearchService.searchByGender(gender, studentsByGender);
	}

	public Collection<Student> processSearchByType(
			Type type,
			StudentCatalogs<Type> studentsByTypes) {
		
		return studentSearchService.searchByType(type, studentsByTypes);
	}

	public Collection<Student> processSearchByTypeAndGender(
			Type type,
			Gender gender, StudentCatalogs<Type> studentsByTypes,
			StudentCatalogs<Gender> studentsByGender) {

		return studentSearchService.searchByTypeAndGender(
				type,
				gender,
				studentsByTypes,
				studentsByGender);
	}

	public boolean processExit(String filename) {
		return dataService.persist(filename);
	}

	/**
	 * Parse the command provided in a string
	 * The objective of this method is to return a Command object
	 * containing the right command instance and all its options
	 * 
	 * @param text a string containing a command
	 * @return a Command instance containing its options
	 * @throws BadRequestException if something goes wrong
	 */
	private Command parseCommand(String text) throws BadRequestException {
		try {
			
			String[] tokens = text.split(" ");
			String cmd = tokens[0];
			text = text.substring(text.indexOf(" ") + 1);
			text = text.replaceAll(tokens[0], "");
			String[] options = text.split(" ");

			HashMap<String, String> opts = new HashMap<String, String>();

			for (String option : options) {
				String[] optAndValue = option.split("=");
				opts.put(optAndValue[0], optAndValue[1]);
			}

			return Command.getCommand(cmd, opts);

		} catch (Exception e) {
			throw new BadRequestException(e);
		}
	}
}
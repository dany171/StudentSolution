package com.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.exceptions.BadDeleteException;
import com.exceptions.BadSearchException;
import com.exceptions.BadStudentException;
import com.exceptions.BadUpdateException;
import com.exceptions.ParseCommandException;
import com.model.Student;
import com.model.Student.Gender;
import com.model.Student.Type;
import com.search.StudentSearchService;
import com.server.data.Consumer;
import com.server.data.DataService;
import com.server.data.PropertyTypeMap;

public class CommandExecutor implements Consumer {

	private DataService dataService;
	
	private StudentSearchService searchService;
	
	private String filename = "input.csv";

	public CommandExecutor(DataService dataService) {
	
		this.dataService = dataService;
		
		boolean dataLoaded = this.dataService.load(filename);
		
		System.out.println("load data: " + dataLoaded);
	}

	public String execute(String command) {

		Command cmd;

		try {
			cmd = parseCommand(command);

			if (cmd == Command.CREATE) {
				
				return executeCreateStudent(cmd);
				
			} else if (cmd == Command.UPDATE) {
				
				return executeUpdateStudent(cmd);
				
			} else if (cmd == Command.DELETE) {
				
				return executeDeleteStudent(cmd);
				
			} else if (cmd == Command.SEARCH_BY_TYPE_AND_GENDER) {//search blocks could be re-factorized into one
				
				return executeSearchByTypeAndGender(cmd);
			
			} else if (cmd == Command.SEARCH_BY_NAME) {
				
				return executeSearchByName(cmd);
				
			} else
			if (cmd == Command.SEARCH_BY_GENDER) {
				
				return executeSearchByGender(cmd);
				
			} else if (cmd == Command.SEARCH_BY_TYPE) {
				
				return executeSearchByType(cmd);
				
			}
			if (cmd == Command.EXIT) {
				
				return executeExit(cmd);
			}

		} catch (ParseCommandException pe) {
			
			return pe.getMessage();
			
		} catch (BadStudentException bse) {
			
			return bse.getMessage();
			
		} catch (BadUpdateException bue) {
			
			return bue.getMessage();
			
		} catch (BadDeleteException bde) {
			
			return bde.getMessage();
			
		} catch (BadSearchException se) {
			
			return se.getMessage();
			
		}

		return "Command not found";

	}

	private String executeCreateStudent(Command cmd) throws BadStudentException {

		HashMap<String, String> options = cmd.getOptions();

		Student student = new Student();

		student.setName(options.get("name"));
		
		student.setGender(Gender.getGender(options.get("gender")));
		
		student.setType(Type.getType(options.get("type")));

		if (student.getName() == null || 
			student.getName().equalsIgnoreCase("") ||
			student.getGender() == null ||
			student.getType() == null) {
			
			throw new BadStudentException("Missing name, gender or type");
		}

		try {

			Student newStudent = processSave(student);
			
			return "Student Created:" + newStudent.toString();

		} catch (Exception e) {
			
			throw new BadStudentException("Error while saving", e);
			
		}
	}

	private String executeUpdateStudent(Command cmd) throws BadUpdateException {
		
		HashMap<String, String> options = cmd.getOptions();

		Student student = new Student();

		student.setId(new Long(options.get("id")));
		
		student.setName(options.get("name"));
		
		student.setGender(Gender.getGender(options.get("gender")));
		
		student.setType(Type.getType(options.get("type")));

		if (student.getId() == null ||
			student.getName() == null ||
			student.getName().equalsIgnoreCase("")||
			student.getGender() == null ||
			student.getType() == null
			) {
			
			throw new BadUpdateException("Missing id, name, gender or type");
			
		}

		try {

			Student updatedStudent = processUpdate(student);
			
			return "Student updated:" + updatedStudent.toString();

		} catch (Exception e) {
			
			throw new BadUpdateException("Error while updating", e);
			
		}
	}

	private String executeDeleteStudent(Command cmd) throws BadDeleteException {
		
		HashMap<String, String> options = cmd.getOptions();
		
		Long id;
		
		try {
			
			String strId = options.get("id");
			
			id = new Long(strId);
			
		} catch (Exception e) {
			
			throw new BadDeleteException("Id empty or incompatible", e);
			
		}

		try {
			
			processDelete(id);
			
			return "Student with ID: " + id + " was deleted";
			
		} catch (Exception e) {
			
			throw new BadDeleteException("Error while deleting", e);
			
		}
	}

	private String executeSearchByName(Command cmd) throws BadSearchException {

		HashMap<String, String> options = cmd.getOptions();
		
		String name = options.get("name");

		if (name == null) {
			
			throw new BadSearchException("Missing name option");
		}

		try {

			Student student = processSearchByName(name, dataService.getStudentsByName());
			
			if (student == null) {
				
				return "Student not found";
				
			} else {
				
				return student.toString();
				
			}

		} catch (Exception e) {
			
			throw new BadSearchException("Error while searching by name", e);
			
		}
	}

	private String executeSearchByGender(Command cmd) throws BadSearchException {

		HashMap<String, String> options = cmd.getOptions();
		
		String gender = options.get("gender");

		if (gender == null) {
			
			throw new BadSearchException("Missing gender option");
			
		}

		try {

			Collection<Student> students = processSearchByGender(
					Gender.getGender(gender), dataService.getStudentsByGender());
			
			if (students == null || students.isEmpty()) {
				
				return "No students found";
				
			} else {
				
				return students.toString();
			}

		} catch (Exception e) {
			
			throw new BadSearchException("Error while searching by gender", e);
			
		}

	}

	private String executeSearchByType(Command cmd) throws BadSearchException {

		HashMap<String, String> options = cmd.getOptions();
		
		String type = options.get("type");

		if (type == null) {
			
			throw new BadSearchException("Missing type option");
			
		}

		try {

			Collection<Student> students = processSearchByType(
					Type.getType(type), dataService.getStudentsByType());
			
			if (students == null || students.isEmpty()) {
				
				return "No students found";
				
			} else {
				
				return students.toString();
				
			}

		} catch (Exception e) {
			
			throw new BadSearchException("Error while searching by gender", e);
			
		}

	}

	private String executeSearchByTypeAndGender(Command cmd) throws BadSearchException {

		HashMap<String, String> options = cmd.getOptions();
		
		String type = options.get("type");
		
		String gender = options.get("gender");

		if (type == null) {
			
			throw new BadSearchException("Missing type option");
			
		}
		if (gender == null) {
			
			throw new BadSearchException("Missing gender option");
			
		}

		try {

			Collection<Student> students = processSearchByTypeAndGender(
					Type.getType(type), Gender.getGender(gender),
					dataService.getStudentsByType(),
					dataService.getStudentsByGender());
			
			if (students == null || students.isEmpty()) {
				
				return "No students found";
				
			} else {
				
				return students.toString();
				
			}
		} catch (Exception e) {
			
			throw new BadSearchException("Error while searching by type and gender", e);
			
		}
	}

	public String executeExit(Command cmd) {

		HashMap<String, String> options = cmd.getOptions();
		
		boolean persist = new Boolean(options.get("persist"));

		if (persist) {
			
			boolean exitSuccess = processExit(filename);

			if (exitSuccess) {
				
				return "All data persisted. bye";
				
			} else {
				
				return "Could not persist data. bye";
				
			}
		} else {
			
			return "Ok, we are not saving anything! :) bye";
			
		}
	}

	@Override
	public Student processSave(Student student) {
		
		return dataService.save(student);
		
	}

	@Override
	public void processDelete(Long id) {
		
		dataService.delete(id);
	}

	@Override
	public Student processUpdate(Student student) {
		
		return dataService.update(student);
	}

	public void setStudentSearchService(StudentSearchService sss) {
		
		this.searchService = sss;
	}

	public Student processSearchByName(String name, Map<String, Student> studentsByName) {
		
		return searchService.searchByName(name, studentsByName);
	}

	public Collection<Student> processSearchByGender(Gender gender,
			PropertyTypeMap<Gender> studentsByGender) {
		
		return searchService.searchByGender(gender, studentsByGender);
	}

	public Collection<Student> processSearchByType(Type type,
			PropertyTypeMap<Type> studentsByTypes) {
		
		return searchService.searchByType(type, studentsByTypes);
	}

	public Collection<Student> processSearchByTypeAndGender(Type type,
			Gender gender, PropertyTypeMap<Type> studentsByTypes,
			PropertyTypeMap<Gender> studentsByGender) {

		return searchService.searchByTypeAndGender(type, gender, studentsByTypes, studentsByGender);
	}

	public boolean processExit(String filename) {
		
		return dataService.persist(filename);
	}

	private Command parseCommand(String text) throws ParseCommandException {

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
			
			throw new ParseCommandException(e);
		}
	}

	private enum Command {
		
		CREATE,
		UPDATE,
		DELETE,
		SEARCH_BY_NAME,
		SEARCH_BY_GENDER,
		SEARCH_BY_TYPE,
		SEARCH_BY_TYPE_AND_GENDER,
		EXIT;

		private HashMap<String, String> options;

		public static Command getCommand(String text, HashMap<String, String> opts) {

			Command cmd = null;

			if (text.equalsIgnoreCase("create")) {
				
				cmd = Command.CREATE;
				cmd.setOptions(opts);

			} else if (text.equalsIgnoreCase("update")) {
				
				cmd = Command.UPDATE;
				cmd.setOptions(opts);
				
			} else if (text.equalsIgnoreCase("delete")) {
				
				cmd = Command.DELETE;
				cmd.setOptions(opts);
				
			} else if (text.equalsIgnoreCase("search")) {
				
				if (opts.get("type") != null && opts.get("gender") != null) {
					
					cmd = Command.SEARCH_BY_TYPE_AND_GENDER;
					
				} else if (opts.get("name") != null) {
					
					cmd = Command.SEARCH_BY_NAME;
					
				} else if (opts.get("gender") != null) {
					
					cmd = Command.SEARCH_BY_GENDER;
					
				} else if (opts.get("type") != null) {
					
					cmd = Command.SEARCH_BY_TYPE;
					
				}
				
				cmd.setOptions(opts);
				
			} else if (text.equalsIgnoreCase("exit")) {
				
				cmd = Command.EXIT;
				
				cmd.setOptions(opts);
			}
			
			return cmd;
		}

		public HashMap<String, String> getOptions() { return options; }

		public void setOptions(HashMap<String, String> options) { this.options = options; }

	}
}

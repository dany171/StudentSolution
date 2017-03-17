package command;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;

public class EditCommand extends CommandStrategy<Student>{

	DataService dataService;
	Student student;
	
	public EditCommand(DataService dataService, String options) throws BadRequestException{
		super(options);
		this.dataService = dataService;
		
		this.student = new Student();
		
		String idString = this.options.get("name");
		Long id = new Long (idString);
		
		String nameString = this.options.get("name");
		
		String typeString = this.options.get("type");
		Type type = Type.getType(typeString);
		
		String genderString = this.options.get("gender");
		Gender gender = Gender.getGender(genderString);
		
		String timestampString = this.options.get("timestamp");
		Long timestamp = new Long (timestampString);
		
		student.setId(id);
		student.setName(nameString);
		student.setType(type);
		student.setGender(gender);
		student.setTimestamp(timestamp);
	}
	
	@Override
	public Result<Student> execute() {
		Student editResult = dataService.update(student);
		Result<Student> res = new Result<Student>(editResult); 
		return res;
	} 
}
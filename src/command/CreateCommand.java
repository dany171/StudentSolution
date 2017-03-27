package command;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
public class CreateCommand extends Command<Student>{
	
	DataService dataService;
	Student student;
	
	public CreateCommand(final DataService dataService, final String options) throws BadRequestException{
		super(options);
		this.dataService = dataService;
		
		this.student = new Student();
		
		String nameString = this.options.get("name");
		
		String typeString = this.options.get("type");
		Type type = Type.getType(typeString);
		
		String genderString = this.options.get("gender");
		Gender gender = Gender.getGender(genderString);
		
		student.setName(nameString);
		student.setType(type);
		student.setGender(gender);
	}
	
	@Override
	public Result<Student> execute() {
		Student saveResult = dataService.save(student);
		Result<Student> res = new Result<Student>(saveResult); 
		return res;
	}
}
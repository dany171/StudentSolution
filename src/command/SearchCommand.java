package command;

import java.util.Collection;

import com.exceptions.BadRequestException;
import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
import com.server.search.Criteria;
import com.server.search.StudentSearchService;

public class SearchCommand extends Command<Collection<Student>>{

	private StudentSearchService studentSearchService;
	private DataService dataService;
    private Criteria criteria;
	
	public SearchCommand(StudentSearchService studentSearchService, DataService dataService, String options) throws BadRequestException{
		super(options);
		this.studentSearchService = studentSearchService;
		this.dataService = dataService;
		
		String name = this.options.get("name");
		
		String typeString = this.options.get("type");
		Type type = Type.getType(typeString);
		
		String genderString = this.options.get("gender");
		Gender gender = Gender.getGender(genderString);
		
		criteria = new Criteria(name, type, gender);
	}
	
	@Override
	public Result<Collection<Student>> execute() {
		Collection<Student> searchRes = studentSearchService.search(criteria, dataService);
		Result<Collection<Student>> res = new Result<Collection<Student>>(searchRes);
		return res;
	}
}

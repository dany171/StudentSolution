package command;

import com.exceptions.BadRequestException;
import com.server.data.DataService;

public class DeleteCommand extends Command<Boolean>{

	private DataService dataService;
	private Long studentId;
	
	public DeleteCommand(DataService dataService, String options) throws BadRequestException{
		super(options);
		this.dataService = dataService;
		String id = this.options.get("id");
		this.studentId = new Long(id);
	}
	
	@Override
	public Result<Boolean> execute() {
		dataService.delete(studentId);
		Result<Boolean> res = new Result<Boolean>(true); 
		return res;
	}
}
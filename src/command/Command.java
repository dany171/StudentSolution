package command;

import java.util.HashMap;
import java.util.Map;

import com.exceptions.BadRequestException;

public abstract class Command<T> {

	protected String name;
	protected Map<String,String> options;
	
	public abstract Result<T> execute();
	
	public Command(String input) throws BadRequestException {
		try {
			
			String[] tokens = input.split(" ");
			name = tokens[0];
			
			int firstOptionIndex = input.indexOf(" ") + 1;
			String optionsText = input.substring(firstOptionIndex);
			
			//text = optionsText.replaceAll(tokens[0], "");
			String[] opts = optionsText.split(" ");

			options = new HashMap<String, String>();

			for (String option : opts) {
				String[] optAndValue = option.split("=");
				options.put(optAndValue[0], optAndValue[1]);
			}
		} catch (Exception e) {
			throw new BadRequestException("Error while parsing",e);
		}
	}
}
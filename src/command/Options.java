package command;

import java.util.HashMap;
import java.util.Map;

public class Options {

	Map<String,String> options;
	
	public Options(){
		options = new HashMap<String,String>();
	}
	
	public void put(String key, String value){
		options.put(key, value);
	}
	
	public String get(String key){
		return options.get(key);
	}
	
	public void remove(String key){
		options.remove(key);
	}
}

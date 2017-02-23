package com.server;

import java.util.HashMap;
/**
 * 
 * Represents a command
 * 
 * Every command has a set of options that could modify 
 * the command behavior. We hold them in a map
 * 
 * @author Daniel Echalar
 *
 */
public enum Command {
	
	CREATE,
	UPDATE,
	DELETE,
	SEARCH_BY_NAME,
	SEARCH_BY_GENDER,
	SEARCH_BY_TYPE,
	SEARCH_BY_TYPE_AND_GENDER,
	EXIT;

	// PROPERTIES
	private HashMap<String, String> options;

	// METHODS
	/**
	 * Determines the command type based in a keyword and set its options to it.
	 * @param text a string containing a command keyword
	 * @param opts the set of options for the command
	 * @return a Command instance containing its options
	 */
	public static Command getCommand(String text, HashMap<String, String> opts) {

		Command cmd = null;
		text = text.toLowerCase();

		switch (text) {
		case "create":
			cmd = Command.CREATE;
			cmd.setOptions(opts);
			break;
		case "update":
			cmd = Command.UPDATE;
			cmd.setOptions(opts);
			break;
		case "delete":
			cmd = Command.DELETE;
			cmd.setOptions(opts);
			break;
		case "search":
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
			break;
		case "exit":
			cmd = Command.EXIT;
			cmd.setOptions(opts);
			break;
		default:
			return null;
		}
		
		return cmd;
	}

	public HashMap<String, String> getOptions() { return options; }

	public void setOptions(HashMap<String, String> options) { this.options = options; }

}
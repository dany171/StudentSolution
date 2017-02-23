package com.server.search;

/**
 * Builder to get a BasicStudentSearchService instance
 * @author Daniel Echalar
 *
 */
public class BasicStudentSearchServiceBuilder implements IStudentSearchServiceBuilder{

	private StudentSearchService studentSearchService;
	
	public BasicStudentSearchServiceBuilder(){
		studentSearchService = new BasicStudentSearchService();
	}
	
	@Override
	public StudentSearchService getResult() {
		return studentSearchService;
	}
}

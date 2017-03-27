package com.server.search;

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

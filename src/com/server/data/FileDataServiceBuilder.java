package com.server.data;

/**
 * Builder to get a FileDataService instance
 * @author Daniel Echalar
 *
 */
public class FileDataServiceBuilder implements IDataServiceBuilder{

	private DataService dataService;
	
	public FileDataServiceBuilder(){
		dataService = new FileDataService();
	}
	
	@Override
	public DataService getResult() {
		return dataService;
	}

}

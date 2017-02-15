package com.server.data;

import com.search.BasicStudentSearchService;
import com.server.DataManager;

public class FileDataServiceInjector implements DataServiceInjector{

	@Override
	public Consumer getConsumer() {
		return new DataManager(new FileDataService());
	}

}

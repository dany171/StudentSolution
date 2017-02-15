package com.search;

import com.model.Student.Gender;
import com.model.Student.Type;

public interface SearchConsumer {
	public void processSearchByName(String name);
	public void processSearchByType(Type type);
	public void processSearhByTypeAndGender(Type type, Gender gender);
}

package com.test;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.DataService;
import com.server.data.FileDataService;
import com.server.data.FileDataServiceBuilder;
import com.server.search.BasicStudentSearchService;
import com.server.search.BasicStudentSearchServiceBuilder;
import com.server.search.Criteria;
import com.server.search.IStudentSearchServiceBuilder;
import com.server.search.StudentSearchService;

public class SearchStudentTests extends TestCase {

	private StudentSearchService searchService = new BasicStudentSearchService();
	private DataService dataService = new FileDataService();
    
    private Student student1;     
    private Student student2;
    private Student student3;
    private Student student4;
    private Student student5;
    private Student student6;
    private Student student7;
    
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		FileDataServiceBuilder fileDataServiceBuilder = new FileDataServiceBuilder();
		dataService = fileDataServiceBuilder.getResult();
		
		IStudentSearchServiceBuilder studentSearchServiceBuilder = new BasicStudentSearchServiceBuilder();   
		searchService = studentSearchServiceBuilder.getResult();
		
		createStudents();
	}
	
	public void testSearchStudentsWithSimilarNames(){
		
		String nameToSearch = "dan";
		
		Criteria criteria = new Criteria(nameToSearch,null,null);
		
		Collection<Student> foundStudents = searchService.search(criteria, dataService);
		
		boolean foundStudentsNumber = foundStudents.size() == 5;
		
		assert(foundStudentsNumber);
		
		Iterator<Student> iterator = foundStudents.iterator();
		
		while(iterator.hasNext()){
			Student studentFound = iterator.next();
			String foundName = studentFound.getName();
			
			boolean similarNames = foundName.contains(nameToSearch);
			assert(similarNames);
		}
	}
	
	public void testSearchStudentsByGender(){
		Gender genderToSearch = Gender.FEMALE;
		Criteria criteria = new Criteria(null, null, genderToSearch);
		
		Collection<Student> foundStudents = searchService.search(criteria, dataService);
		
		boolean foundStudentsNumber = foundStudents.size() == 2;
		assert(foundStudentsNumber);
	}
	
	public void testSearchStudentsByType(){
		Type typeToSearch = Type.KINDERGARDEN;
		Criteria criteria = new Criteria(null, typeToSearch, null);
		
		Collection<Student> foundStudents = searchService.search(criteria, dataService);
		
		boolean foundStudentsNumber = foundStudents.size() == 3;
		assert(foundStudentsNumber);
	}
	
	public void testSearchStudentsByNameAndType(){
		String nameToSearch = "dan";
		Type typeToSearch = Type.ELEMENTARY;
		
		Criteria criteria = new Criteria(nameToSearch, typeToSearch, null);
		
		Collection<Student> foundStudents = searchService.search(criteria, dataService);
		
		boolean foundStudentsNumber = foundStudents.size() == 2;
		assert(foundStudentsNumber);
	}
	
	public void testSearchStudentsByNameAndTypeAndGender(){
		String nameToSearch = "dan";
		Type typeToSearch = Type.ELEMENTARY;
		Gender genderToSearch = Gender.FEMALE;
		
		Criteria criteria = new Criteria(nameToSearch, typeToSearch, genderToSearch);
		
		Collection<Student> foundStudents = searchService.search(criteria, dataService);
		
		boolean foundStudentsNumber = foundStudents.size() == 1;
		assert(foundStudentsNumber);
	}
	
	private void createStudents(){
		student1 = new Student();
		student1.setName("daniel");
		Gender gender1 = Gender.MALE;
		student1.setGender(gender1);
		Type type1 = Type.ELEMENTARY;
		student1.setType(type1);
		dataService.save(student1);
		
		student2 = new Student();
		student2.setName("dan");
		Gender gender2 = Gender.MALE;
		student1.setGender(gender2);
		Type type2 = Type.KINDERGARDEN;
		student2.setType(type2);
		dataService.save(student2);
		
		student3 = new Student();
		student3.setName("dani");
		Gender gender3 = Gender.FEMALE;
		student3.setGender(gender3);
		Type type3 = Type.UNIVERSITY;
		student3.setType(type3);
		dataService.save(student3);
		
		student4 = new Student();
		student4.setName("pedro");
		Gender gender4 = Gender.MALE;
		student4.setGender(gender4);
		Type type4 = Type.HIGHSCHOOL;
		student4.setType(type4);
		dataService.save(student4);
		
		student5 = new Student();
		student5.setName("daniels jack");
		Gender gender5 = Gender.MALE;
		student5.setGender(gender5);
		Type type5 = Type.ELEMENTARY;
		student5.setType(type5);
		dataService.save(student5);
		
		student6 = new Student();
		student6.setName("daniela");
		Gender gender6 = Gender.FEMALE;
		student1.setGender(gender6);
		Type type6 = Type.KINDERGARDEN;
		student6.setType(type6);
		dataService.save(student6);
		
		student7 = new Student();
		student7.setName("danubio");
		Gender gender7 = Gender.MALE;
		student7.setGender(gender7);
		Type type7 = Type.KINDERGARDEN;
		student7.setType(type7);
		dataService.save(student7);
	}
}
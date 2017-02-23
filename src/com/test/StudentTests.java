package com.test;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

import com.model.Gender;
import com.model.Student;
import com.model.Type;
import com.server.data.StudentCatalogs;
import com.server.data.StudentIndexByName;
import com.server.search.BasicStudentSearchService;

public class StudentTests extends TestCase {

	final String lexic = "abcdefghijklmnopqrstuvwxyz";
	final java.util.Random rand = new java.util.Random();
	final Set<String> identifiers = new HashSet<String>();
    final BasicStudentSearchService searchService = new BasicStudentSearchService(); 
    
    StudentIndexByName index;
    StudentCatalogs<Gender> studentsByGender;
    StudentCatalogs<Type> studentsByType;
    Student studentToSearch;
    
    int maleCounter;
    int kindergardenCounter;
    int kindergardenAndMaleCounter;
    
	@Override
	protected void setUp() throws Exception {
		Long idCounter = 1L;
		
		this.index = new StudentIndexByName();
		this.studentsByGender = new StudentCatalogs<Gender>();
		this.studentsByType = new StudentCatalogs<Type>();
		
		this.maleCounter = 0;
		this.kindergardenCounter = 0;
		this.kindergardenAndMaleCounter = 0;
		
		for(int i = 0 ; i<=50000; i++){
			Student s = new Student();
			
			String name = getRandomName();
			Type type = getRandomType();
			Gender gender = getRandomGender();
			Long id = idCounter;
			Date d = new Date();
			Long timestamp = d.getTime();
			
			s.setName(name);
			s.setGender(gender);
			s.setId(id);
			s.setTimestamp(timestamp);
			s.setType(type);
			
			index.put(s);
			studentsByGender.put(s, s.getGender());
			studentsByType.put(s, s.getType());
			
			if(i==30000){
				this.studentToSearch = s;
			}
			if(gender.equals(Gender.MALE)){
				maleCounter++;
			}
			
			if(type.equals(Type.KINDERGARDEN)){
				kindergardenCounter++;
			}
			
			if(type.equals(Type.KINDERGARDEN) && gender.equals(Gender.MALE)){
				kindergardenAndMaleCounter++;
			}
			
			idCounter++;
		}
		super.setUp();
	}
	
	private Gender getRandomGender(){
		
		boolean gender = Math.random() < 0.5;
		String strGender = gender? "male":"female";
		Gender res =  Gender.getGender(strGender);
		
		return res;
	}
	
	private Type getRandomType(){
		
		String[] typesStr = {"kindergarden", "elementary", "highschool", "university"}; 
		int i = 0 + (int)(Math.random() * (typesStr.length-1));
		Type res =  Type.getType(typesStr[i]);
		
		return res;
	}
	
	private String getRandomName(){
		    StringBuilder builder = new StringBuilder();
		    while(builder.toString().length() == 0) {
		        int length = rand.nextInt(5)+5;
		        for(int i = 0; i < length; i++) {
		            builder.append(lexic.charAt(rand.nextInt(lexic.length())));
		        }
		        if(identifiers.contains(builder.toString())) {
		            builder = new StringBuilder();
		        }
		    }
		    return builder.toString();
	}
	
	@Test
	public void testSearchByName() {
		System.out.println("---testSearchByName");
		System.out.println("Name to Search :"+studentToSearch.getName());
		
		Date start = new Date();
		Collection<Student> res = searchService.searchByName(studentToSearch.getName(), index);
		Date end = new Date();
		
		long diffInMilliseconds = end.getTime()-start.getTime();
		
		for(Student rs: res){
			String nameToSearch = studentToSearch.getName();
			String foundName = rs.getName();
			
			boolean containsName = foundName.contains(nameToSearch);
			assert(containsName);
		}
		System.out.println("Found in "+diffInMilliseconds+" milliseconds");
	}
	
	@Test
	public void testSearchByIncompleteName() {
		System.out.println("---testSearchByIncompleteName");
		String nameToSearch = studentToSearch.getName();
		String incompletename = nameToSearch.substring(0, nameToSearch.length()-2);
		
		System.out.println("Incomplete name to Search :"+incompletename);
		
		Date start = new Date();
		Collection<Student> res = searchService.searchByName(incompletename, index);
		Date end = new Date();
		
		long diffInMilliseconds = end.getTime()-start.getTime();
		
		System.out.println("Students found:");
		for(Student rs: res){
			String foundName = rs.getName();
			boolean containsName = foundName.contains(incompletename);
			assert(containsName);
			System.out.println(foundName);
		}
		
		System.out.println("Found in "+diffInMilliseconds+" milliseconds");
	}
	
	@Test
	public void testSearchByGender() {
		System.out.println("---testSearchByGender");
		
		
		Date start = new Date();
		Collection<Student> res = searchService.searchByGender(Gender.MALE, studentsByGender);
		Date end = new Date();
		
		long diffInMilliseconds = end.getTime()-start.getTime();
		
		assert(res.size()==this.maleCounter);
		System.out.println("Number of male students: "+this.maleCounter);
		System.out.println("Found in "+diffInMilliseconds+" milliseconds");
	}
	
	@Test
	public void testSearchByType() {
		System.out.println("---testSearchByType");
		
		Date start = new Date();
		Collection<Student> res = searchService.searchByType(Type.KINDERGARDEN, studentsByType);
		Date end = new Date();
		
		long diffInMilliseconds = end.getTime()-start.getTime();
		
		assert(res.size()==this.kindergardenCounter);
		System.out.println("Nnumber of kindergarden students: "+this.kindergardenCounter);
		System.out.println("Found in "+diffInMilliseconds+" milliseconds");
	}
	
	@Test
	public void testSearchByTypeAndGender() {
		System.out.println("---testSearchByTypeAndGender");
		
		Date start = new Date();
		Collection<Student> res = searchService.searchByTypeAndGender(Type.KINDERGARDEN, 
				Gender.MALE, 
				studentsByType,
				studentsByGender);
		Date end = new Date();
		
		long diffInMilliseconds = end.getTime()-start.getTime();
		
		assert(res.size()==this.kindergardenAndMaleCounter);
		
		System.out.println("Nnumber of kindergarden And male students: "+
				this.kindergardenAndMaleCounter);
		
		System.out.println("Found in "+diffInMilliseconds+" milliseconds");
	}
}

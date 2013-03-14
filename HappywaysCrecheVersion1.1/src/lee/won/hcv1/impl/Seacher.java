package lee.won.hcv1.impl;

import java.util.ArrayList;
import java.util.List;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.exceptions.PersonNotFoundException;

/**
 * 
 * @author Won Lee
 * @version 1.0 b102217
 * 
 * b102055:	This class was created and binary search was introduced for ID search.
 * b102217:	seachName method was added.
 *
 */
public class Seacher {
	private List<Person> persons;

	public Seacher(List<Person> creche) {
		// TODO Auto-generated constructor stub
		persons = creche;
	}
	
	public Person searchId(int lookingfor) throws PersonNotFoundException{
		//System.out.println("----------------------------------------");
		int size = persons.size()-1;
		int i = size/2;
		Person ps = persons.get(i);
		if(ps.getID() > lookingfor){
			ps = searchId(lookingfor, 0, i-1);
		}else if(ps.getID() < lookingfor){
			ps = searchId(lookingfor, i+1, size);
		}
		return ps;
	}

	private Person searchId(int lookingfor, int min, int max) throws PersonNotFoundException{
		//System.out.println("min:"+min+" mid:"+(min + (max - min)/2)+" max:"+max);
		if(min > max || max < min){
			throw new PersonNotFoundException(lookingfor, "");
		}
		int i = min + (max - min)/2;
		Person ps = persons.get(i);
		if(ps.getID() > lookingfor){
			ps = searchId(lookingfor, min, i-1);
		}else if(ps.getID() < lookingfor){
			ps = searchId(lookingfor, i+1, max);	
		}
		return ps;
	}
	
	public List<Person> seachName(String fName, String sName) throws PersonNotFoundException{
		List<Person> list = new ArrayList<Person>();
		if(!fName.equals("") && !sName.equals("")){
			for(Person ps: persons){
				if(ps.getFirstname().equals(fName)&&ps.getSurname().equals(sName)){
					list.add(ps);
				}
			}
		}else if(fName.equals("") && !sName.equals("")){
			for(Person ps: persons){
				if(ps.getSurname().equals(sName)){
					list.add(ps);
				}
			}			
		}else if(!fName.equals("") && sName.equals("")){
			for(Person ps: persons){
				if(ps.getFirstname().equals(fName)){
					list.add(ps);
				}
			}						
		}
		if(list.size()<=0){
			throw new PersonNotFoundException(-1, fName+":"+sName);
		}
		return list;
	}
}

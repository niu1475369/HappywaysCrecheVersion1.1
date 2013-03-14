package lee.won.hcv1.abs;

import java.io.Serializable;

import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.InvalidInputException;

/**
 * 
 * This is abstract class. 
 * This class holds basic field and control id number by static field "idBase".
 * 
 * @author Won Lee
 * @version 1.0 b220701 (build number format 'day/hour/min',)
 * 
 * b021233:	Create basic 5 field and static id field.
 * 			Create constructor which accept parameter and throws exception.
 * 			Create required abstract method.
 * b030742: Add getter and setter for some field
 * 			=> Can ID duplicate within subclasses or must be unique for entire subclasses of Person?
 * 			=> must be unique within subclasses of Person.
 * b140955: Rule for id was decided
 * 			=> ID will be divided by 1000000 and allocated for each subclasses.
 * 			=> 0 ~ 1000000 for Person class, 1000001 ~ 2000000 for Child class.
 * 			=> Think about creating a class that control ID rule.
 * b151346:	Implements Serializable
 * b220701: abstract method "setNewID" was implemented for defrag ID.
 * 
 */
public abstract class Person implements Serializable, Comparable{
	
	protected PersonType type;
	protected String firstname;
	protected String surname;
	protected String gender;
	protected int id;

	/**
	 * 
	 * @param type person type such as "Parent" and "Child"
	 * @param firstname first name
	 * @param surname surname
	 * @param gender gender which has to be "Male" or "Female"
	 * @throws InvalidInputException if firstname or/and surname are null or blank, or/and
	 * 			gender is not properly typed, this will be thrown
	 * 
	 * Check the ID rule written in Person class, some ID is already allocated.
	 */
	public Person(String firstname, String surname, String gender) throws InvalidInputException {
		//check name is propery typed.
		if((firstname == null || firstname.trim().equals(""))||(surname == null || surname.trim().equals(""))){
			//throws exception if name is null or blank
			throw new InvalidInputException("The name is imcomplete");
		}else{
			this.firstname = firstname;
			this.surname = surname;
		}
		this.gender = gender;
	}
	
	public abstract String getPersonType();
	
	public abstract int getID();
	
	public abstract void setNewID() throws IdOverflowException;

	public void setID(int id){
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public PersonType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PersonType type) {
		this.type = type;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int compareTo(Object arg0) {
		Person ps = (Person) arg0;
		if(id != ps.getID()){
			return id - ps.getID();
		}
		if(!firstname.equals(ps.getFirstname())){
			return firstname.compareTo(ps.getFirstname());
		}
		if(!surname.equals(ps.getSurname())){
			return surname.compareTo(ps.getSurname());
		}
		return 0;
	}
	
	public String toString(){
		return "type: "+type+" firstname: "+firstname+" surname: "+surname+" gender: "+gender+" id: "+id;
	}

}
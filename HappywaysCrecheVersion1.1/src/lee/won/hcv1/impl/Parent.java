/**
 * 
 */
package lee.won.hcv1.impl;

import java.util.ArrayList;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.abs.PersonType;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.InvalidInputException;

/**
 * This class extends Person class and holds Child classes.
 * 
 * @author Won Lee
 * @version 0.1 b141633
 * b141633: variable 'id' is provided by PersonID class now
 *
 */
public class Parent extends Person {

	protected static int baseId_Parent = 0;

	/**
	 * @param type
	 * @param firstname
	 * @param surname
	 * @param gender
	 * @throws InvalidInputException
	 * @throws IdOverflowException 
	 */
	public Parent(String firstname, String surname, String gender)
			throws InvalidInputException, IdOverflowException {
		super(firstname, surname, gender);
		// TODO Auto-generated constructor stub
		type = PersonType.Parent;
		id = PersonID.obtainParentID();
	}

	@Override
	public String getPersonType() {
		// TODO Auto-generated method stub
		return "Parent";
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	public String toString(){
		return super.toString();
	}

	@Override
	public void setNewID() throws IdOverflowException {
		// TODO Auto-generated method stub
		id = PersonID.obtainParentID();
	}
}

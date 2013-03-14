package lee.won.hcv1.impl;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.abs.PersonType;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.InvalidInputException;

/**
 * This class extends Person and is held by Parent class.
 * 
 * @author Won Lee
 * @version 0.1 b141633
 * b030747:	"fee" field is added.
 * b141633: variable 'id' is now provided by PersonID class now.
 * 
 */
public class Child extends Person {
	
	protected double fee;
	protected int parentId;

	/**
	 * @param type
	 * @param firstname
	 * @param surname
	 * @param gender
	 * @throws InvalidInputException
	 * @throws IdOverflowException 
	 */
	public Child(String firstname, String surname, String gender, double fee, int parentId)
			throws InvalidInputException, IdOverflowException {
		super(firstname, surname, gender);
		// TODO Auto-generated constructor stub
		type = PersonType.Child;
		this.fee = fee;
		this.parentId = parentId;
		id = PersonID.obtainChildID();
	}
	
	

	@Override
	public String getPersonType() {
		// TODO Auto-generated method stub
		return "Child";
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * @return the fee
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public String toString(){
		return super.toString()+" fee: "+fee+" ParentId:"+parentId;
	}

	@Override
	public void setNewID() throws IdOverflowException {
		// TODO Auto-generated method stub
		id = PersonID.obtainChildID();
	}

}

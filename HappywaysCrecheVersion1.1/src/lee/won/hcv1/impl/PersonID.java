package lee.won.hcv1.impl;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.impl.*;

/**
 * 
 * @author Won Lee
 * @version 1.0 b220725
 * b141633:	Basic method were created
 * b220725:	resetId method is added for defrag ID
 */
public class PersonID {
	
	private static int parentLength = 1;
	private static int parentBase = 0;
	private static int parentCurrent = 0;

	private static int childLength = 1;
	private static int childBase = 1000000*parentLength;
	private static int childCurrent = 1000000*parentLength;
	
	public static void resetId(){
		parentBase = 0;
		parentCurrent = 0;
		childBase = 1000000*parentLength;
		childCurrent = 1000000*parentLength;
	}
	
	public static int obtainParentID() throws IdOverflowException{
		if(parentCurrent >= parentBase + 1000000*parentLength){
			throw new IdOverflowException("There is no more stock of IDs for Parent in the system.\n"+parentCurrent);
		}
		return ++parentCurrent;
	}

	public static int obtainChildID() throws IdOverflowException{
		if(childCurrent >= childBase + 1000000*childLength){
			throw new IdOverflowException("There is no more stock of IDs for Child in the system.\n"+childCurrent);
		}
		return ++childCurrent;
	}
	
	public static int getParentLevel(){
		return parentBase;
	}
	
	public static int getChildLevel(){
		return childBase/1000000;
	}
	
	public static int getLevel(Person ps){
		int level = -1;
		if(ps instanceof Parent){
			level = parentBase/1000000;
		}else if(ps instanceof Child){
			level = childBase/1000000;
		}
		//System.out.println(level);
		return level;
	}
	
	public static void changeParentLength(int parentLength){
		int preChiSize = PersonID.childCurrent - PersonID.childBase;
		PersonID.parentLength = parentLength;
		childBase = 1000000*parentLength;
		childCurrent = childBase+preChiSize;
	}

	public static void changeChildLength(int childLength){
		int preSize = PersonID.childCurrent - PersonID.childBase;
		PersonID.childLength = childLength;
		childBase = 1000000*parentLength;
		
	}

	/**
	 * @return the parentLength
	 */
	public static int getParentLength() {
		return parentLength;
	}

	/**
	 * @param parentLength the parentLength to set
	 */
	public static void setParentLength(int parentLength) {
		PersonID.parentLength = parentLength;
	}

	/**
	 * @return the parentBase
	 */
	public static int getParentBase() {
		return parentBase;
	}

	/**
	 * @param parentBase the parentBase to set
	 */
	public static void setParentBase(int parentBase) {
		PersonID.parentBase = parentBase;
	}

	/**
	 * @return the parentCurrent
	 */
	public static int getParentCurrent() {
		return parentCurrent;
	}

	/**
	 * @param parentCurrent the parentCurrent to set
	 */
	public static void setParentCurrent(int parentCurrent) {
		PersonID.parentCurrent = parentCurrent;
	}

	/**
	 * @return the childLength
	 */
	public static int getChildLength() {
		return childLength;
	}

	/**
	 * @param childLength the childLength to set
	 */
	public static void setChildLength(int childLength) {
		PersonID.childLength = childLength;
	}

	/**
	 * @return the childBase
	 */
	public static int getChildBase() {
		return childBase;
	}

	/**
	 * @param childBase the childBase to set
	 */
	public static void setChildBase(int childBase) {
		PersonID.childBase = childBase;
	}

	/**
	 * @return the childCurrent
	 */
	public static int getChildCurrent() {
		return childCurrent;
	}

	/**
	 * @param childCurrent the childCurrent to set
	 */
	public static void setChildCurrent(int childCurrent) {
		PersonID.childCurrent = childCurrent;
	}
	
	
}

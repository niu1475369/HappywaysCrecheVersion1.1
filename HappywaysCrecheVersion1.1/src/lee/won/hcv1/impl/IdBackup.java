package lee.won.hcv1.impl;

/**
 * Instance of this class holds old ID of instance of Person (ID before defragged)
 * and new ID of instance of Person (ID after defragged)
 * ID Backup is to keep compatibility between old ID and new ID
 * 
 * @author Won
 * @version 1.0  b220750
 * 
 * b220750:	basic was implemented
 */
public class IdBackup {
	private int oldId;
	private int newId;

	public IdBackup(int oldId, int newId) {
		super();
		this.oldId = oldId;
		this.newId = newId;
	}

	/**
	 * @return the oldId
	 */
	public int getOldId() {
		return oldId;
	}

	/**
	 * @return the newId
	 */
	public int getNewId() {
		return newId;
	}
	
	public String toString(){
		return "Old ID: "+oldId+" = New ID: "+newId;
	}

}

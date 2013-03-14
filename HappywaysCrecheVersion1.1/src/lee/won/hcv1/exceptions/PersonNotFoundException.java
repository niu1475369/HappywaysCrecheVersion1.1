package lee.won.hcv1.exceptions;

/**
 * 
 * @author Won
 * @version 1.0 b102055
 * 
 * b102055:	This exception was created.
 *
 */
public class PersonNotFoundException extends Exception {
	String name;
	int id;

	public PersonNotFoundException() {
		// TODO Auto-generated constructor stub
		super("No such person found");
	}

	public PersonNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PersonNotFoundException(int id, String arg0) {
		super("No such person found");
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = arg0;
	}
	
	public PersonNotFoundException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PersonNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}

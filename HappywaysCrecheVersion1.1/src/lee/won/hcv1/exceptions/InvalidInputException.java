package lee.won.hcv1.exceptions;

/**
 * 
 * This exception is high level exception that is for invalid input.
 * 
 * @author Won Lee
 * @version 1.0 b021233
 * 
 * b021233:	Create this class that extends Exception
 *
 */
public class InvalidInputException extends Exception {

	public InvalidInputException() {
		// TODO Auto-generated constructor stub
		super("Invalid Input Detected");
	}

	public InvalidInputException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidInputException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidInputException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}

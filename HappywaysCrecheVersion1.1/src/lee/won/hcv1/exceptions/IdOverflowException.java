package lee.won.hcv1.exceptions;

public class IdOverflowException extends Exception {

	public IdOverflowException() {
		// TODO Auto-generated constructor stub
		super("There is no more stock of IDs in the system.");
	}

	public IdOverflowException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public IdOverflowException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public IdOverflowException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}

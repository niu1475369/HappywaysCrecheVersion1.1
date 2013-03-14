package lee.won.hcv1.exceptions;

import java.util.List;

public class NoPrimaryKeyFoundException extends Exception {
	private List<Integer> errorIdList;

	public NoPrimaryKeyFoundException() {
		// TODO Auto-generated constructor stub
	}

	public NoPrimaryKeyFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NoPrimaryKeyFoundException(List<Integer> list) {
		// TODO Auto-generated constructor stub
		errorIdList = list;
	}

	public NoPrimaryKeyFoundException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NoPrimaryKeyFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public List<Integer> getErrorId(){
		return errorIdList;
	}

}

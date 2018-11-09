package littlePoneyBack.exception;

public class UserCudException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7172472183378002861L;
	
	 public UserCudException() {
	        super();
	    }
	    public UserCudException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public UserCudException(String message) {
	        super(message);
	    }
	    public UserCudException(Throwable cause) {
	        super(cause);
	    }

}

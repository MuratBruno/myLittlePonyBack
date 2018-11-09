package littlePoneyBack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class RaceNotFoundException extends Exception {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2635069492542572616L;
	public RaceNotFoundException() {
	        super();
	    }
	    public RaceNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public RaceNotFoundException(String message) {
	        super(message);
	    }
	    public RaceNotFoundException(Throwable cause) {
	        super(cause);
	    }
}

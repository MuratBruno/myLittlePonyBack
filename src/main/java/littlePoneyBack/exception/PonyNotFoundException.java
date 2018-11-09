package littlePoneyBack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class PonyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3693814390992351137L;

	 public PonyNotFoundException() {
	        super();
	    }
	    public PonyNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public PonyNotFoundException(String message) {
	        super(message);
	    }
	    public PonyNotFoundException(Throwable cause) {
	        super(cause);
	    }
}


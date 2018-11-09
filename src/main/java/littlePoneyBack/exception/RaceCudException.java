package littlePoneyBack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class RaceCudException extends Exception {
	private static final long serialVersionUID = 3693814390992351137L;

	 public RaceCudException() {
	        super();
	    }
	    public RaceCudException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public RaceCudException(String message) {
	        super(message);
	    }
	    public RaceCudException(Throwable cause) {
	        super(cause);
	    }
}

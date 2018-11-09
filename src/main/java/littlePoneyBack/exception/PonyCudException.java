package littlePoneyBack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class PonyCudException extends Exception {
	private static final long serialVersionUID = 3693814390992351137L;

	 public PonyCudException() {
	        super();
	    }
	    public PonyCudException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public PonyCudException(String message) {
	        super(message);
	    }
	    public PonyCudException(Throwable cause) {
	        super(cause);
	    }
}


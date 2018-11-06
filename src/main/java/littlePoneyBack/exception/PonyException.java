package littlePoneyBack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class PonyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3693814390992351137L;


}


package cz.muni.fi.pa165.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Martin Wörgötter
 *
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Resource validation failed.")
public class InvalidRequestException extends RuntimeException {
	public InvalidRequestException(String message) {
        super(message);
    }
}

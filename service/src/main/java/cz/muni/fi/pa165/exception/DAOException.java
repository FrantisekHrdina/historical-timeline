package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;

/*
 * @author Martin Wörgötter
 */
public class DAOException extends DataAccessException {
	
	public DAOException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for DataAccessException.
	 *
	 * @param msg   the detail message
	 * @param cause the root cause (usually from using a underlying
	 */
	public DAOException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

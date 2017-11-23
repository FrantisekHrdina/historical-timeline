package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;

/*
 * @author Martin Wörgötter
 */
public class DAOException extends DataAccessException {
	
	public DAOException(String msg) {
		super(msg);
	}

}

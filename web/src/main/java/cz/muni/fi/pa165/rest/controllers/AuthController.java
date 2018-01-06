package cz.muni.fi.pa165.rest.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.dto.CredentialsDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;

/**
 * 
 * @author Martin Wörgötter
 *
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_AUTH)
public class AuthController {
	
	@Inject
	UserFacade userFacade;
	
	final static Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public final String authenticate(@RequestBody CredentialsDTO credentials) {
		
		UserDTO user = userFacade.findUserByEmail(credentials.getLogin());
		
        MessageDigest messageDigest;
        
        try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(credentials.getPassword().getBytes());
	        String hash = new String(messageDigest.digest());
	        if (user != null) {
				if (hash.equals(user.getPasswordHash())) {
					return user.isIsTeacher() ? "teacher" : "student"; 
				} else {
					throw new ResourceNotFoundException("Bad password.");
				}
	        } else {
	        	throw new ResourceNotFoundException("Bad login.");
	        }
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
/*
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update("1234".getBytes());
	        String studentPwd = new String(messageDigest.digest());
	        messageDigest.update("4321".getBytes());
	        String teacherPwd = new String(messageDigest.digest());

			messageDigest.update(credentials.getPassword().getBytes());
	        String hash = new String(messageDigest.digest());
	        if (credentials.getLogin().equals("student")) {
				if (hash.equals(studentPwd)) {
					return "student"; 
				}
	        } else if (hash.equals(teacherPwd)) {
				return "teacher";
			} else {
				throw new ResourceNotFoundException("Bad credentials.");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
	}
	*/

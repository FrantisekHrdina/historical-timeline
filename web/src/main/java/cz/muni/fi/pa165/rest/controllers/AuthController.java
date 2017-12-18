package cz.muni.fi.pa165.rest.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.dto.CredentialsDTO;
import cz.muni.fi.pa165.rest.ApiUris;

/**
 * 
 * @author Martin Wörgötter
 *
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_AUTH)
public class AuthController {
	final static Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public final String authenticate(@RequestBody CredentialsDTO credentials) {
		
        MessageDigest messageDigest;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update("1234".getBytes());
	        String studentPwd = new String(messageDigest.digest());
	        messageDigest.update("4321".getBytes());
	        String teacherPwd = new String(messageDigest.digest());
	        
	        
	        if (credentials.getLogin() == "student") {
				messageDigest.update(credentials.getPassword().getBytes());
				if (new String(messageDigest.digest()) == studentPwd) {
					return "{role: student}"; 
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
}

package cz.muni.fi.pa165.rest.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.dto.CredentialsDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.TimelineFacade;
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

	public class AuthContainer {
		private String role;
		private List<TimelineDTO> timelines;
		private String username;

		public AuthContainer(String role, List<TimelineDTO> timelines,
				String username) {
			this.role = role;
			this.timelines = timelines;
			this.username = username;
		}

		public List<TimelineDTO> getTimelines() {
			return timelines;
		}

		public void setTimelines(List<TimelineDTO> timelines) {
			this.timelines = timelines;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

	@Inject
	UserFacade userFacade;

	@Inject
	TimelineFacade timelineFacade;

	final static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public final AuthContainer authenticate(
			@RequestBody CredentialsDTO credentials) {

		UserDTO user = userFacade.findUserByEmail(credentials.getLogin());

		MessageDigest messageDigest;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(credentials.getPassword().getBytes());
			String hash = new String(messageDigest.digest());
			if (user != null) {
				if (hash.equals(user.getPasswordHash())) {
					List<TimelineDTO> timelines = user.isIsTeacher()
							? timelineFacade.getAllTimelines()
							: timelineFacade
									.findTimelinesByUserEmail(user.getEmail());
					String username = user.getForename() + ' '
							+ user.getSurname();
					return new AuthContainer(
							user.isIsTeacher() ? "teacher" : "student",
							timelines, username);
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
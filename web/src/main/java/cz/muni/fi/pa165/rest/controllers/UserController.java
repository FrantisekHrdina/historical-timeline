package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.exception.DAOException;
import cz.muni.fi.pa165.facade.SeminarGroupFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotModifiedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tibor Bujdoš
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserController {
    
    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserFacade userFacade;
    
    @Inject
    private SeminarGroupFacade seminarGroupFacade;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> getUsers() {
	logger.debug("rest getUsers()");
        try {
            List<UserDTO> users = new ArrayList<>();
            users.addAll(userFacade.findAllStudents());
            users.addAll(userFacade.findAllTeachers());
            return users;
	} catch (DAOException ex) {
            logger.error("rest findAllStudents() or findAllTeachers() error");
	}
        return null;
    }
    
    @RequestMapping(value = "/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> getStudents() {
	logger.debug("rest getStudents()");
        try {
            return userFacade.findAllStudents();
	} catch (DAOException ex) {
            logger.error("rest findAllStudents() error");
	}
            return null;
    }
    
    @RequestMapping(value = "/teachers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> getTeachers() {
	logger.debug("rest getTeachers()");
        try {
            return userFacade.findAllTeachers();
	} catch (DAOException ex) {
            logger.error("rest findAllTeachers() error");
	}
            return null;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUser(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getUser({})", id);
        try {
            UserDTO user = userFacade.findUser(id);
            if (user != null) {
                return user;
            }
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
        catch (DAOException e) {
            logger.error("rest findUser({}) error", id, e);
            throw new ResourceNotFoundException("User with id: " + id + " not found"); 
        }
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO createUser(@RequestBody UserDTO user) {
	logger.debug("rest createUser()");
	try {
            Long id = userFacade.addUser(user);
            return userFacade.findUser(id);
        }
        catch (DAOException e) {
            logger.error("rest createUser({}) error", e);
            throw new ResourceAlreadyExistingException("User could not be created", e);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void updateUser(@PathVariable("id") long id, @RequestBody UserDTO user) {
        logger.debug("rest updateUser({})", id);
        UserDTO existingUser;
        try {
            existingUser = userFacade.findUser(id);
        } catch (DAOException e) {
            logger.error("rest findUser in updateUser({}) error", id, e);
            throw new ResourceNotFoundException("User with id: " + id + " not found", e);
        }
        try {
            userFacade.editUser(user);
        } catch (DAOException e) {
            logger.error("rest updateUser({}) error", id, e);
            throw new ResourceNotModifiedException("User with id: " + id + " could not be updated", e);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void removeUser(@PathVariable("id") long id) {
        logger.debug("rest removeUser({})", id);
        try {
            userFacade.removeUser(id);
        } catch (DAOException e) {
            logger.error("rest removeUser({}) error", id, e);
            throw new ResourceNotFoundException("User with id: " + id + " could not be removed");
        }
    }
    
    @RequestMapping(value = "/{userid}/addseminargroup/{groupid}", method = RequestMethod.PUT)
    public void addSeminarGroup(@PathVariable("userid") Long userId, @PathVariable("groupid") Long seminarGroupId) {
        try {
            UserDTO user = userFacade.findUser(userId);
            SeminarGroupDTO group = seminarGroupFacade.findGroup(seminarGroupId);
            user.addSeminarGroup(group);
            userFacade.editUser(user);
        } catch (DAOException e) {
            logger.error("rest add Seminar Group", userId, e);
            throw new ResourceNotModifiedException();
        }
    }
    
}

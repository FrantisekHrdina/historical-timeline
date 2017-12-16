package cz.muni.fi.pa165.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotDeletableException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.dto.SeminarGroupCreateDTO;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.exception.DAOException;
import cz.muni.fi.pa165.facade.SeminarGroupFacade;

/**
 * 
 * @author Martin Wörgötter
 *
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_GROUPS)
public class SeminarGroupController {

	final static Logger logger = LoggerFactory
			.getLogger(SeminarGroupController.class);

	@Inject
	private SeminarGroupFacade seminarGroupFacade;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<SeminarGroupDTO> getSeminarGroups() {
		logger.debug("rest getSeminarGroups()");
		try {
			return seminarGroupFacade.findAllGroups();
		} catch (DAOException ex) {
			logger.debug("rest findAllGroups() error");
		}
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final SeminarGroupDTO getSeminarGroup(@PathVariable("id") long id) {
		logger.debug("rest getSeminarGroup({})", id);
		try {
			SeminarGroupDTO seminarGroup = seminarGroupFacade.findGroup(id);
			if (seminarGroup == null)
				throw new ResourceNotFoundException(
						"SeminarGroup " + id + " not found.");
			return seminarGroup;
		} catch (DAOException ex) {
			logger.debug("rest findGroup({}) error", id, ex);
			throw new ResourceNotFoundException(
					"SeminarGroup " + id + " not found.");
		}

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public final SeminarGroupDTO createSeminarGroup(
			@RequestBody @Valid SeminarGroupCreateDTO seminarGroupCreateDTO,
			BindingResult bindingResult) {
		logger.debug("rest createSeminarGroup()");
		Long id = null;
		if (bindingResult.hasErrors()) {
			logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation.");
		}
		try {
			id = seminarGroupFacade.saveGroup(seminarGroupCreateDTO);
		} catch (IllegalArgumentException | DAOException ex) {
			logger.debug("createSeminarGroup({}) error", seminarGroupCreateDTO,
					ex);
		}
		try {
			return seminarGroupFacade.findGroup(id);
		} catch (DAOException ex) {
			logger.debug("rest error retrieving SeminarGroup after save.");
		}
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public final void removeSeminarGroup(@PathVariable("id") long id) {
		logger.debug("rest removeSeminarGroup({})", id);
		try {
			seminarGroupFacade.removeGroup(id);
		} catch (IllegalArgumentException | DAOException ex) {
			logger.debug("removeSeminarGroup({}) error", id, ex);
			throw new ResourceNotDeletableException(
					"SeminarGroup " + id + " cannot be deleted.");
		}
	}

}

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
import cz.muni.fi.pa165.dto.SeminarGroupDTO;
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

		return seminarGroupFacade.findAllGroups();
	}

	//TODO: exception
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final SeminarGroupDTO getSeminarGroup(@PathVariable("id") long id) throws Exception {
		logger.debug("rest getSeminarGroup({})", id);

		SeminarGroupDTO seminarGroup = seminarGroupFacade.findGroup(id);
		if (seminarGroup == null)
			throw new Exception(
					"SeminarGroup " + id + " not found.");
		return seminarGroup;
	}
	
	//TODO: validation
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final SeminarGroupDTO createSeminarGroup(@RequestBody @Valid SeminarGroupDTO seminarGroupDTO, BindingResult bindingResult) throws Exception {
    	logger.debug("rest createSeminarGroup()");
    	
    	Long id = seminarGroupFacade.saveGroup(seminarGroupDTO);
    	return seminarGroupFacade.findGroup(id);
    }
    
    //TODO: exception
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.GET)
    public final void removeSeminarGroup(@PathVariable("id") long id) {
    	logger.debug("rest removeSeminarGroup({})", id);
    	seminarGroupFacade.removeGroup(id);
    }

}

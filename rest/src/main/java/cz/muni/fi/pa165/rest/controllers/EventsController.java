package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(ApiUris.ROOT_URI_EVENTS)
public class EventsController {

    final static Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Inject
    private EventFacade eventFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEvents() {
        logger.debug("rest getEvents()");

        return eventFacade.findAllEvents();
    }
}

package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotDeletableException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author František Hrdina
 *
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_EVENTS)
public class EventsController {

    final static Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Inject
    private EventFacade eventFacade;

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEvents() {
        logger.debug("rest getEvents()");

        return eventFacade.findAllEvents();
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO getEvent(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getEvent({})", id);
        EventDTO eventDTO = eventFacade.findEvent(id);

        if (eventDTO != null) {
            return eventDTO;
        }
        else {
            throw new ResourceNotFoundException("Event not found, id:" + id);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO createEvent(@RequestBody EventDTO event) throws Exception {
        logger.debug("rest createEvent");

        try {
            Long id = eventFacade.addEvent(event);
            return eventFacade.findEvent(id);
        }
        catch (Exception ex) {
            throw new ResourceAlreadyExistingException("Event was not created", ex);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO updateEvent(@PathVariable("id") long id,
                                    @RequestBody EventDTO event) {
        logger.debug("rest updateEvent({})", id);

        EventDTO foundedEvent;

        try {
            foundedEvent = eventFacade.findEvent(id);
        } catch (ResourceNotFoundException ex) {
            logger.error("rest findEvent in updateEvent({})", id, ex);
            throw new ResourceNotFoundException("Event to update not found, id:" + id, ex);
        }

        //EventDTO toBeUpdated = event;

        try {
            eventFacade.editEvent(event);
            return event;
        } catch (Exception ex) {
            logger.error("updateEvent({})", id, ex);
            throw new ResourceNotModifiedException("Event not updated, id:" + id, ex);
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteEvent(@PathVariable("id") long id) {
        logger.debug("deleteEvent({})", id);

        try {
            eventFacade.removeEvent(id);
        } catch (ResourceNotFoundException ex) {
            logger.error("deletevEvent({})", id, ex);
            throw new ResourceNotFoundException("Event to delete not found, id:" + id);
        } catch (DataAccessException ex) {
            logger.error("deleteEvent({}) constraint violation", id, ex);
            throw new ResourceNotDeletableException("Event not deleted, id" + id, ex);
        }

    }


}

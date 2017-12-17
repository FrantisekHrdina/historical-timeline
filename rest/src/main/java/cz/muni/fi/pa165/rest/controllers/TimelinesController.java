package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.CommentDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.facade.TimelineFacade;
import cz.muni.fi.pa165.rest.ApiUris;
import cz.muni.fi.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotDeletableException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

/**
 * @author Martin Kocisky, 421131
 */

@RestController
@RequestMapping(ApiUris.ROOT_URI_TIMELINES)
public class TimelinesController {

    private final static Logger logger = LoggerFactory.getLogger(TimelinesController.class);

    @Autowired
    private TimelineFacade timelineFacade;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
//    public TimelineDTO createTimeline(@RequestBody TimelineDTO timelineDTO) {
    public TimelineDTO createTimeline(@RequestBody TimelineCreateDTO timelineDTO) {
        logger.debug("rest createTimeline");
        try {
            Long id = timelineFacade.createTimeline(timelineDTO);
            return timelineFacade.getTimelineById(id);
        } catch (Exception e) {
            logger.error("createTimeline", e);
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(
            value = "/{id}/addcomment",
            method = RequestMethod.PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public TimelineDTO addComment(@PathVariable("id") Long timelineId, @RequestBody CommentDTO comment) {
        logger.debug("rest addComment");
        try {
            timelineFacade.addComment(timelineId, comment.getComment());
            return timelineFacade.getTimelineById(timelineId);
        } catch (Exception e) {
            logger.error("addComment", timelineId, e);
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(
            value = "/{id}/setseminargroup/{gid}",
            method = RequestMethod.PUT)
    public void setSeminarGroup(@PathVariable("id") Long timelineId, @PathVariable("gid") Long seminarGroupId) {
        try {
            timelineFacade.setSeminarGroup(timelineId, seminarGroupId);
        } catch (Exception e) {
            logger.error("setSeminarGroup", timelineId, e);
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(
            value = "/{id}/removeseminargroup",
            method = RequestMethod.PUT)
    public void removeSeminarGroup(@PathVariable("id") Long timelineId) {
        try {
            timelineFacade.removeSeminarGroup(timelineId);
        } catch (Exception e) {
            logger.error("removeSeminarGroup", timelineId, e);
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(
            value = "/{id}/addevent/{eid}",
            method = RequestMethod.PUT)
    public void addEvent(@PathVariable("id") Long timelineId, @PathVariable("eid") Long eventId) {
        try {
            timelineFacade.addEvent(timelineId, eventId);
        } catch (Exception e) {
            logger.error("addEvent", timelineId, e);
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(
            value = "/{id}/removeevent/{eid}",
            method = RequestMethod.PUT)
    public void removeEvent(@PathVariable("id") Long timelineId, @PathVariable("eid") Long eventId) {
        try {
            timelineFacade.removeEvent(timelineId, eventId);
        } catch (Exception e) {
            logger.error("removeEvent", timelineId, e);
            throw new ResourceNotModifiedException();
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE)
    public TimelineDTO getTimelineById(@PathVariable("id") Long timelineId) {
        try {
            return timelineFacade.getTimelineById(timelineId);
        } catch (Exception e) {
            logger.error("getTimelineById", timelineId, e);
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE)
    public List<TimelineDTO> getAllTimelines() {
        try {
            return timelineFacade.getAllTimelines();
        } catch (Exception e) {
            logger.error("getAllTimelines", e);
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteTimeline(@PathVariable("id") Long timelineId) {
        logger.debug("deleteTimeline", timelineId);
        try {
            timelineFacade.deleteTimeline(timelineId);
        } catch (Exception e) {
            logger.error("deleteTimeline", timelineId, e);
            throw new ResourceNotDeletableException();
        }
    }
}

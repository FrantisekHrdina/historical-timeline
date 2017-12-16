package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.SeminarGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Kocisky, 421131
 */
@Service
public class TimelineFacadeImpl implements TimelineFacade {

    @Inject
    private TimelineService timelineService;

    @Inject
    private SeminarGroupService seminarGroupService;

    @Inject
    private EventService eventService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createTimeline(TimelineDTO timelineDTO) {
        Timeline timeline = new Timeline();
        timeline.setName(timelineDTO.getName());
        timeline.setComments(timelineDTO.getComments());

        List<Event> events = new ArrayList<>();
        List<EventDTO> eventDTOs = timelineDTO.getEvents();
        for (EventDTO e : eventDTOs) {
            events.add(eventService.findEvent(e.getId()));
        }
        timeline.setEvents(events);

        SeminarGroup seminarGroup = seminarGroupService.findGroup(timelineDTO.getSeminarGroup().getId());
        timeline.setSeminarGroup(seminarGroup);

        timelineService.createTimeline(timeline);
        return timeline.getId();
    }

    @Override
    public void addComment(Long timelineId, String comment) {
        timelineService.addComment(timelineId, comment);
    }

    @Override
    public void setSeminarGroup(Long timelineId, Long seminarGroupId) {
        timelineService.setSeminarGroupToTimeline(timelineId, seminarGroupId);
    }

    @Override
    public void removeSeminarGroup(Long timelineId) {
        timelineService.removeSeminarGroupFromTimeline(timelineId);
    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {
        timelineService.addEventToTimeline(timelineId, eventId);
    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {
        timelineService.removeEventFromTimeline(timelineId, eventId);
    }

    @Override
    public TimelineDTO getTimelineById(Long timelineId) {
        Timeline timeline = timelineService.getTimelineById(timelineId);
        return beanMappingService.mapTo(timeline,TimelineDTO.class);
    }

    @Override
    public List<TimelineDTO> getAllTimelines() {
        List<Timeline> timelineList = timelineService.getAllTimelines();
        return beanMappingService.mapTo(timelineList,TimelineDTO.class);
    }

    @Override
    public void deleteTimeline(Long timelineId) {
        timelineService.deleteTimeline(timelineId);
    }
}

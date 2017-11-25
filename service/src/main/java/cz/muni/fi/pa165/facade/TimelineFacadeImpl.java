package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.sql.Time;
import java.util.List;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineFacadeImpl implements TimelineFacade {

    final static Logger log = LoggerFactory.getLogger(TimelineFacadeImpl.class);

    @Inject
    private TimelineService timelineService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createTimeline(TimelineDTO timelineDTO) {
        Timeline timeline = new Timeline();
        timeline.setName(timelineDTO.getName());
        timeline.setComments(timelineDTO.getComments());

        timelineService.createTimeline(timeline);
        return timeline.getId();
    }

    @Override
    public void addComment(Long timelineId, String comment) {
        timelineService.addComment(timelineId, comment);
    }

    @Override
    public void addSeminarGroup(Long timelineId, Long seminarGroupId) {
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
        return beanMappingService.mapTo(timelineService.getTimelineById(timelineId),TimelineDTO.class);
    }

    @Override
    public List<TimelineDTO> getAllTimelines() {
        return beanMappingService.mapTo(timelineService.getAllTimelines(),TimelineDTO.class);
    }

    @Override
    public void deleteTimeline(Long timelineId) {
        timelineService.deleteTimeline(timelineId);
    }
}

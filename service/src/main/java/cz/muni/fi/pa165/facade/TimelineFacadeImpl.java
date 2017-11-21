package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineFacadeImpl implements TimelineFacade {

    final static Logger log = LoggerFactory.getLogger(TimelineFacadeImpl.class);

    @Override
    public Long createTimeline(TimelineDTO t) {
        return null;
    }

    @Override
    public void addComment(Long timelineId, String comment) {

    }

    @Override
    public void addSeminarGroup(Long timelineId, Long seminarGroupId) {

    }

    @Override
    public void removeSeminarGroup(Long timelineId, Long seminarGroupId) {

    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {

    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {

    }

    @Override
    public TimelineDTO getTimelineById(Long timelineId) {
        return null;
    }

    @Override
    public List<TimelineDTO> getAllTimelines() {
        return null;
    }

    @Override
    public void deleteTimeline(Long timelineId) {

    }
}

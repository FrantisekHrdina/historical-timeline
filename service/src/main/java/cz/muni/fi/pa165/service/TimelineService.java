package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Timeline;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Timeline} entity.
 * @author Martin Kocisky, 421131
 */
@Service
public interface TimelineService {

    public Long createTimeline(Timeline t);

    public void addComment(Long timelineId, String comment);

    public void setSeminarGroup(Long timelineId, Long seminarGroupId);
    public void removeSeminarGroup(Long timelineId);

    public void addEvent(Long timelineId, Long eventId);
    public void removeEvent(Long timelineId, Long eventId);

    public Timeline getTimelineById(Long timelineId);
    public List<Timeline> getAllTimelines();

    public void deleteTimeline(Long timelineId);

}

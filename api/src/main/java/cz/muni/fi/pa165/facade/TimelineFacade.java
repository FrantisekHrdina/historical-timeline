package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineDTO;

import java.util.List;

/**
 * @author Martin Kocisky, 421131
 */
public interface TimelineFacade {

    public Long createTimeline(TimelineDTO t);

    public void addComment(Long timelineId, String comment);

    public void addSeminarGroup(Long timelineId, Long seminarGroupId);
    public void removeSeminarGroup(Long timelineId);

    public void addEvent(Long timelineId, Long eventId);
    public void removeEvent(Long timelineId, Long eventId);

    public TimelineDTO getTimelineById(Long timelineId);
    public List<TimelineDTO> getAllTimelines();

    public void deleteTimeline(Long timelineId);
}

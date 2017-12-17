package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;

import java.util.List;

/**
 * An interface that defines a facade access to the TimelineService.
 * @author Martin Kocisky, 421131
 */
public interface TimelineFacade {

    /**
     * Create a timeline from DTO object.
     * @param t
     * @return id of the new timeline
     */
    public Long createTimeline(TimelineCreateDTO t);

    /**
     * Add comment to a timeline.
     * @param timelineId
     * @param comment
     */
    public void addComment(Long timelineId, String comment);

    /**
     * Set seminar group for the timeline.
     * @param timelineId
     * @param seminarGroupId
     */
    public void setSeminarGroup(Long timelineId, Long seminarGroupId);

    /**
     * Remove seminar group from a timeline.
     * @param timelineId
     */
    public void removeSeminarGroup(Long timelineId);

    /**
     * Add an event to a timeline.
     * @param timelineId
     * @param eventId
     */
    public void addEvent(Long timelineId, Long eventId);

    /**
     * Remove event from timeline.
     * @param timelineId
     * @param eventId
     */
    public void removeEvent(Long timelineId, Long eventId);

    /**
     * Get timeline with given id.
     * @param timelineId
     * @return
     */
    public TimelineDTO getTimelineById(Long timelineId);

    /**
     * Get all timelines.
     * @return
     */
    public List<TimelineDTO> getAllTimelines();

    /**
     * Delete timeline with given id.
     * @param timelineId
     */
    public void deleteTimeline(Long timelineId);
}

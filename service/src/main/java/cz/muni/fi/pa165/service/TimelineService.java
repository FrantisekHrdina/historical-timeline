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

    /**
     * Create timeline.
     * @param t
     * @return
     */
    public Long createTimeline(Timeline t);

    /**
     * Add comment to timeline.
     * @param timelineId
     * @param comment
     */
    public void addComment(Long timelineId, String comment);

    /**
     * Set seminar group for timeline.
     * @param timelineId
     * @param seminarGroupId
     */
    public void setSeminarGroupToTimeline(Long timelineId, Long seminarGroupId);

    /**
     * Remove seminar group from timeline.
     * @param timelineId
     */
    public void removeSeminarGroupFromTimeline(Long timelineId);

    /**
     * Add event to timeline.
     * @param timelineId
     * @param eventId
     */
    public void addEventToTimeline(Long timelineId, Long eventId);

    /**
     * Remove event from timeline.
     * @param timelineId
     * @param eventId
     */
    public void removeEventFromTimeline(Long timelineId, Long eventId);

    /**
     * Get timeline with given id..
     * @param timelineId
     * @return
     */
    public Timeline getTimelineById(Long timelineId);

    /**
     * Get all timelines.
     * @return
     */
    public List<Timeline> getAllTimelines();

    /**
     * Delete timeline.
     * @param timelineId
     */
    public void deleteTimeline(Long timelineId);

}

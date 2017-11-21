package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Timeline;

import java.util.List;

/**
 *
 * @author Martin Kocisky, 421131
 */
public class TimelineServiceImpl implements TimelineService {
    @Override
    public Long createTimeline(Timeline t) {
        return null;
    }

    @Override
    public void addComment(Long timelineId, String comment) {

    }

    @Override
    public void setSeminarGroup(Long timelineId, Long seminarGroupId) {

    }

    @Override
    public void removeSeminarGroup(Long timelineId) {

    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {

    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {

    }

    @Override
    public Timeline getTimelineById(Long timelineId) {
        return null;
    }

    @Override
    public List<Timeline> getAllTimelines() {
        return null;
    }

    @Override
    public void deleteTimeline(Long timelineId) {

    }
}

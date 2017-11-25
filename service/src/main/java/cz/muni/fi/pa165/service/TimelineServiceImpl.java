package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin Kocisky, 421131
 */
public class TimelineServiceImpl implements TimelineService {

    @Autowired
    private TimelineDao timelineDao;

    @Autowired
    private SeminarGroupDao seminarGroupDao;

    @Autowired
    private EventDao eventDao;

    @Override
    public Long createTimeline(Timeline t) {
        timelineDao.addTimeline(t);
        return t.getId();
    }

    @Override
    public void addComment(Long timelineId, String comment) {
        Timeline timeline = timelineDao.findTimeline(timelineId);

        List<String> comments = new ArrayList<>(timeline.getComments());
        comments.add(comment);
        timeline.setComments(comments);

        timelineDao.editTimeline(timeline);
    }

    @Override
    public void setSeminarGroupToTimeline(Long timelineId, Long seminarGroupId) {
        Timeline timeline = timelineDao.findTimeline(timelineId);
        SeminarGroup seminarGroup = seminarGroupDao.findGroup(seminarGroupId);

        timeline.setSeminarGroup(seminarGroup);

        List<Timeline> timelines = new ArrayList<>(seminarGroup.getTimelines());
        timelines.add(timeline);
        seminarGroup.setTimelines(timelines);

        timelineDao.editTimeline(timeline);
        seminarGroupDao.editGroup(seminarGroup);
    }

    @Override
    public void removeSeminarGroupFromTimeline(Long timelineId) {
        Timeline timeline = timelineDao.findTimeline(timelineId);
        SeminarGroup seminarGroup = timeline.getSeminarGroup();

        timeline.setSeminarGroup(null);

        List<Timeline> timelines = new ArrayList<>(seminarGroup.getTimelines());
        timelines.remove(timeline);
        seminarGroup.setTimelines(timelines);

        timelineDao.editTimeline(timeline);
        seminarGroupDao.editGroup(seminarGroup);
    }

    @Override
    public void addEventToTimeline(Long timelineId, Long eventId) {
        Timeline timeline = timelineDao.findTimeline(timelineId);
        Event event = eventDao.findEvent(eventId);

        List<Event> events = new ArrayList<>(timeline.getEvents());
        events.add(event);
        timeline.setEvents(events);

        List<Timeline> timelines = new ArrayList<>(event.getTimelines());
        timelines.add(timeline);
        event.setTimelines(timelines);

        timelineDao.editTimeline(timeline);
        eventDao.editEvent(event);
    }

    @Override
    public void removeEventFromTimeline(Long timelineId, Long eventId) {
        Timeline timeline = timelineDao.findTimeline(timelineId);
        Event event = eventDao.findEvent(eventId);

        List<Event> events = new ArrayList<>(timeline.getEvents());
        events.remove(event);
        timeline.setEvents(events);

        List<Timeline> timelines = event.getTimelines();
        timelines.remove(timeline);
        event.setTimelines(timelines);

        timelineDao.editTimeline(timeline);
        eventDao.editEvent(event);
    }

    @Override
    public Timeline getTimelineById(Long timelineId) {
        return timelineDao.findTimeline(timelineId);
    }

    @Override
    public List<Timeline> getAllTimelines() {
        return timelineDao.findAllTimelines();
    }

    @Override
    public void deleteTimeline(Long timelineId) {
        Timeline timeline = timelineDao.findTimeline(timelineId);
        timelineDao.removeTimeline(timeline);
    }
}

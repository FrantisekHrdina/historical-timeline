package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Martin Kocisky, 421131
 */
@Service
public class TimelineServiceImpl implements TimelineService {

    @Autowired
    private TimelineDao timelineDao;

    @Autowired
    private SeminarGroupDao seminarGroupDao;

    @Autowired
    private EventDao eventDao;

    @Override
    public Long createTimeline(Timeline t) {
        if (t == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            timelineDao.addTimeline(t);
            return t.getId();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void addComment(Long timelineId, String comment) {
        if (timelineId == null || comment == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            Timeline timeline = timelineDao.findTimeline(timelineId);

            Set<String> comments = new LinkedHashSet<>(timeline.getComments());
            comments.add(comment);
            timeline.setComments(comments);

            timelineDao.editTimeline(timeline);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void setSeminarGroupToTimeline(Long timelineId, Long seminarGroupId) {
        if (timelineId == null || seminarGroupId == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            removeSeminarGroupFromTimeline(timelineId);

            Timeline timeline = timelineDao.findTimeline(timelineId);
            SeminarGroup seminarGroup = seminarGroupDao.findGroup(seminarGroupId);

            timeline.setSeminarGroup(seminarGroup);

            List<Timeline> timelines = new ArrayList<>(seminarGroup.getTimelines());
            timelines.add(timeline);
            seminarGroup.setTimelines(timelines);

            timelineDao.editTimeline(timeline);
            seminarGroupDao.editGroup(seminarGroup);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void removeSeminarGroupFromTimeline(Long timelineId) {
        if (timelineId == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            Timeline timeline = timelineDao.findTimeline(timelineId);
            SeminarGroup seminarGroup = timeline.getSeminarGroup();

            timeline.setSeminarGroup(null);

            if (seminarGroup != null) {
                List<Timeline> timelines = new ArrayList<>(seminarGroup.getTimelines());
                timelines.remove(timeline);
                seminarGroup.setTimelines(timelines);
                seminarGroupDao.editGroup(seminarGroup);
            }
            timelineDao.editTimeline(timeline);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void addEventToTimeline(Long timelineId, Long eventId) {
        if (timelineId == null || eventId == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            Timeline timeline = timelineDao.findTimeline(timelineId);
            Event event = eventDao.findEvent(eventId);

            if (timeline.getEvents().contains(event)) {
                return;
            }

            List<Event> events = timeline.getEvents();
            events.add(event);
            timeline.setEvents(events);

            event.setTimeline(timeline);

            timelineDao.editTimeline(timeline);
            eventDao.editEvent(event);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }


    }

    @Override
    public void removeEventFromTimeline(Long timelineId, Long eventId) {
        if (timelineId == null || eventId == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            Timeline timeline = timelineDao.findTimeline(timelineId);
            Event event = eventDao.findEvent(eventId);

            List<Event> events = timeline.getEvents();
            events.remove(event);
            timeline.setEvents(events);

            event.setTimeline(null);

            timelineDao.editTimeline(timeline);
            eventDao.editEvent(event);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Timeline getTimelineById(Long timelineId) {
        if (timelineId == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            return timelineDao.findTimeline(timelineId);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Timeline> getAllTimelines() {
        try {
            return timelineDao.findAllTimelines();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void deleteTimeline(Long timelineId) {
        if (timelineId == null) throw new IllegalArgumentException("Null is not acceptable!");
        try {
            Timeline timeline = timelineDao.findTimeline(timelineId);

            for (Event e : timeline.getEvents()) {
                removeEventFromTimeline(timelineId, e.getId());
            }

            timelineDao.removeTimeline(timeline);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

}

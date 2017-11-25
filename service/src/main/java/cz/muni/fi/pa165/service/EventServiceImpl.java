package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author František Hrdina
 */
@Service
public class EventServiceImpl implements EventService{

    //@Autowired
    @Inject
    private EventDao eventDao;

    @Override
    public void addEvent(Event event) {
        eventDao.addEvent(event);
    }

    @Override
    public void removeEvent(Event event) {
        eventDao.removeEvent(event);
    }

    @Override
    public void editEvent(Event event) {
        eventDao.editEvent(event);
    }

    @Override
    public Event findEvent(Long id) {
        return eventDao.findEvent(id);
    }

    @Override
    public List<Event> findAllEvents() {
        return eventDao.findAllEvents();
    }

    @Override
    public List<Event> findEventsInRange(LocalDate from, LocalDate to) {
       return eventDao.findEventsInRange(from, to);
    }

    @Override
    public List<Event> findEventsByLocation(String location) {
        return eventDao.findEventsByLocation(location);
    }
}

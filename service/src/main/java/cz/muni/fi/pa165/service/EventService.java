package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author František Hrdina
 */
@Service
public interface EventService {
    void addEvent(Event event);
    void removeEvent(Event event);
    void editEvent(Event event);
    Event findEvent(Long id);
    List<Event> findAllEvents();
    List<Event> findEventsInRange(LocalDate from, LocalDate to);
    List<Event> findEventsByLocation (String location);
 }

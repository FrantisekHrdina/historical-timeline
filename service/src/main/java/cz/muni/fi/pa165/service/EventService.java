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

    /**
     * Add event
     *
     * @param event Event to be added
     */
    void addEvent(Event event);

    /**
     * Remove given event
     *
     * @param event Given Event
     *
     */
    void removeEvent(Event event);

    /**
     * Edit given event
     *
     * @param event Given event
     */
    void editEvent(Event event);

    /**
     * Find event by id
     * @param id given id
     * @return Founded event
     */
    Event findEvent(Long id);

    /**
     * Find all events
     *
     * @return List of all events
     */
    List<Event> findAllEvents();

    /**
     * Find all events in range from - to
     *
     * @param from Date from
     * @param to Date to
     * @return List of founded events in range from - to
     */
    List<Event> findEventsInRange(LocalDate from, LocalDate to);

    /**
     * Find events by location
     *
     * @param location Given location
     * @return List of events with given location
     */
    List<Event> findEventsByLocation (String location);
 }

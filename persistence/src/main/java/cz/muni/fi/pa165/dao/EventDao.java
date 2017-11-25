package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Event;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Tibor Bujdoš
 */
public interface EventDao {
    
    /**
     * Stores Event
     * 
     * @param event Event to be stored
     */
    void addEvent(Event event);
    
    /**
     * Removes Event
     * 
     * @param event event to be removed
     */
    void removeEvent(Event event);
    
    /**
     * Edits Event
     * 
     * @param event edited event
     */
    void editEvent(Event event);
    
    /**
     * Finds Event by given Id
     * 
     * @param id Id of the desired Event
     * @return desired Event
     */
    Event findEvent(Long id);
    
    /**
     * Finds all Events
     * 
     * @return all Events
     */
    List<Event> findAllEvents();

    /**
     * Finds alee Events in range
     *
     * @param from
     * @param to
     * @return events in range
     */
    List<Event> findEventsInRange(LocalDate from, LocalDate to);
    List<Event> findEventsByLocation (String location);
    
}

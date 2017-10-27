package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Event;
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
    
}

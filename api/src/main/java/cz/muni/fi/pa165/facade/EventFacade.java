package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author František Hrdina
 */
public interface EventFacade {

    /**
     * Add event
     *
     * @param event Event to be added
     * @return Added event
     */
    Long addEvent(EventDTO event);

    /**
     * Remove id by id
     *
     * @param id Given id
     */
    void removeEvent(Long id);

    /**
     * Edit event given in argument
     *
     * @param event Given event
     */
    void editEvent(EventDTO event);

    /**
     * Find event by id
     *
     * @param id given id
     * @return Founded Event
     */
    EventDTO findEvent (Long id);

    /**
     *  Find all events
     *
     * @return List of events
     */
    List<EventDTO> findAllEvents();

    /**
     * Find all events in range from - to
     *
     * @param from Date from
     * @param to Date to
     * @return List of events in range from - to
     */
    List<EventDTO> findEventsInRange(LocalDate from, LocalDate to);

    /**
     * Find events by location
     *
     * @param location Given location
     * @return List of events with given location
     */
    List<EventDTO> findEventsByLocation(String location);
}

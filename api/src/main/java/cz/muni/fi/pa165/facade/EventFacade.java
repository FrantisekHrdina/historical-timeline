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
     * @return
     */
    Long addEvent(EventDTO event);

    /**
     *
     * @param id
     */
    void removeEvent(Long id);

    /**
     *
     * @param event
     */
    void editEvent(EventDTO event);

    /**
     *
     * @param id
     * @return
     */
    EventDTO findEvent (Long id);

    /**
     *
     * @return
     */
    List<EventDTO> findAllEvents();

    /**
     *
     * @param from
     * @param to
     * @return
     */
    List<EventDTO> findEventsInRange(LocalDate from, LocalDate to);

    /**
     *
     * @param location
     * @return
     */
    List<EventDTO> findEventsByLocation(String location);
}

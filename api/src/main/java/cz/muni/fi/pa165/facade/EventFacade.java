package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author František Hrdina
 */
public interface EventFacade {
    Long addEvent(EventDTO event);
    void removeEvent(Long id);
    void editEvent(EventDTO event);
    EventDTO findEvent (Long id);
    List<EventDTO> findAllEvents();
    List<EventDTO> findEventsInRange(LocalDate from, LocalDate to);
    List<EventDTO> findEventsByLocation(String location);
}

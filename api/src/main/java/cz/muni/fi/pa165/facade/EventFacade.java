package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author František Hrdina
 */
public interface EventFacade {
    void addEvent(EventDTO event);
    void removeEvent(EventDTO event);
    void editEvent(EventDTO event);
    void findEvent(Long id);
    List<EventDTO> findAllEvents();
    List<EventDTO> findEventsInRange(LocalDate from, LocalDate to);
    List<EventDTO> findEventsByLocation(String location);
}

package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * @author František Hrdina
 */
@Service
@Transactional
public class EventFacadeImpl implements EventFacade {

    final static Logger log = LoggerFactory.getLogger(EventFacadeImpl.class);

    //@Autowired
    @Inject
    private EventService eventService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long addEvent(EventDTO event) {
        Event mappedEvent = beanMappingService.mapTo(event, Event.class);
        eventService.addEvent(mappedEvent);

        return mappedEvent.getId();
    }

    @Override
    public void removeEvent(Long id) {
        eventService.removeEvent(eventService.findEvent(id));
    }

    @Override
    public void editEvent(EventDTO event) {
        Event mappedEvent = beanMappingService.mapTo(event, Event.class);
        eventService.editEvent(mappedEvent);
    }

    @Override
    public EventDTO findEvent(Long id) {

        return beanMappingService.mapTo(eventService.findEvent(id), EventDTO.class);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        List<Event> allEvents = eventService.findAllEvents();
        return beanMappingService.mapTo(allEvents, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsInRange(LocalDate from, LocalDate to) {
        List<Event> eventsInRange = eventService.findEventsInRange(from, to);
        return beanMappingService.mapTo(eventsInRange, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByLocation(String location) {
        List<Event> eventsByLocation = eventService.findEventsByLocation(location);
        return beanMappingService.mapTo(eventsByLocation, EventDTO.class);
    }
}

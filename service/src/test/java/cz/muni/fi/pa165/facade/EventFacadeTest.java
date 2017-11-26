package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.EventService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author František Hrdina
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class EventFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private EventService eventService;

    @Autowired
    @InjectMocks
    private EventFacade eventFacade;

    @Autowired
    private BeanMappingService beanMappingService;

    private EventDTO event1;
    private EventDTO event2;
    private EventDTO event3;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUp() {
        event1 = new EventDTO();
        event1.setName("Event 1");
        event1.setDate(LocalDate.now());
        event1.setLocation("Here");
        event1.setDescription("Description");

        event2 = new EventDTO();
        event2.setName("Event 2");
        event2.setDate(LocalDate.now());
        event2.setLocation("Here");
        event2.setDescription("Description");

        event3 = new EventDTO();
        event3.setName("Event 3");
        event3.setDate(LocalDate.now());
        event3.setLocation("Here");
        event3.setDescription("Description");

        eventFacade.addEvent(event1);
        eventFacade.addEvent(event2);
        eventFacade.addEvent(event3);
    }

    @Test
    public void findAllEvents() {
        List<EventDTO> allEvents = eventFacade.findAllEvents();
        assertThat(allEvents).hasSize(3).contains(event1, event2, event3);
    }

    @Test
    public void addEvent() {
        EventDTO newEvent = new EventDTO();
        newEvent.setName("New event");
        newEvent.setDate(LocalDate.now());

        eventFacade.addEvent(newEvent);

        List<EventDTO> allEvents = eventFacade.findAllEvents();

        assertThat(allEvents).contains(newEvent);
    }

    @Test
    public void findEvent() {
        EventDTO newEvent = new EventDTO();
        newEvent.setName("New event for find");
        newEvent.setDate(LocalDate.now());
        Long id = eventFacade.addEvent(newEvent);
        assertThat(eventFacade.findEvent(id)).isNotNull();
    }

    @Test
    public void editEvent() {
        EventDTO newEvent = new EventDTO();
        newEvent.setName("New event for update");
        newEvent.setDate(LocalDate.now());
        Long id = eventFacade.addEvent(newEvent);

        newEvent.setId(id);
        newEvent.setName("Updated");
        eventFacade.editEvent(newEvent);

        EventDTO updated = eventFacade.findEvent(id);
        assertThat(updated.getName()).isEqualTo("Updated");

    }

    @Test
    public void removeEvent() {
        EventDTO newEvent = new EventDTO();
        newEvent.setName("New event for delete");
        newEvent.setDate(LocalDate.now());

        Long id = eventFacade.addEvent(newEvent);
        Integer sumOfEventsWith = eventFacade.findAllEvents().size();

        eventFacade.removeEvent(id);

        assertThat(eventFacade.findAllEvents().size()).isEqualTo(sumOfEventsWith - 1);
    }

    @Test
    public void findEventsInRange() {
        EventDTO eventInRange = new EventDTO();
        eventInRange.setName("In range");
        eventInRange.setDate(LocalDate.of(2005,1,1));
        eventFacade.addEvent(eventInRange);

        List<EventDTO> eventsInRange = eventFacade.findEventsInRange(
                LocalDate.of(2004, 12, 31),
                LocalDate.of(2005,1,2));

        assertThat(eventsInRange).hasSize(1).contains(eventInRange);

    }

    @Test
    public void findEventsByLocation() {
        EventDTO eventInLocation = new EventDTO();
        eventInLocation.setName("Location");
        eventInLocation.setDate(LocalDate.now());
        eventInLocation.setLocation("Brno");
        eventFacade.addEvent(eventInLocation);

        List<EventDTO> eventsByLocation = eventFacade.findEventsByLocation("Brno");

        assertThat(eventsByLocation).hasSize(1).contains(eventInLocation);

    }




}

package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.exception.DAOException;
import org.hibernate.service.spi.ServiceException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author František Hrdina
 */
//@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class EventServiceTest extends AbstractTransactionalTestNGSpringContextTests{

    @Mock
    private EventDao eventDao;

   /* @Mock*/
   @Autowired
    private EventService eventService;

/*    @Autowired
    @InjectMocks
    private EventService eventService = new EventServiceImpl();*/

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Event eventInDB1;
    private Event eventInDB2;
    private Event eventInDB3;
    private Event eventWithNullName;
    private Event eventWithNullDate;

    @BeforeMethod
    public void setUp() {
        eventInDB1 = new Event();
        eventInDB1.setName("Event 1");
        eventInDB1.setDate(LocalDate.now());
        eventInDB1.setLocation("Here");
        eventInDB1.setDescription("Description");

        eventInDB2 = new Event();
        eventInDB2.setName("Event 2");
        eventInDB2.setDate(LocalDate.now());
        eventInDB2.setLocation("Here");
        eventInDB2.setDescription("Description");

        eventInDB3 = new Event();
        eventInDB3.setName("Event 3");
        eventInDB3.setDate(LocalDate.now());
        eventInDB3.setLocation("Here");
        eventInDB3.setDescription("Description");


        eventWithNullDate = new Event();
        eventWithNullDate.setName("Event null date");
        eventWithNullDate.setDate(null);

        eventWithNullName = new Event();
        eventWithNullName.setName(null);
        eventWithNullName.setDate(LocalDate.now());

        eventService.addEvent(eventInDB1);
        eventService.addEvent(eventInDB2);
        eventService.addEvent(eventInDB3);
    }

    @Test
    public void findAllEvents() {
        List<Event> allEvents = eventService.findAllEvents();

        assertThat(allEvents).hasSize(3).contains(eventInDB1, eventInDB2, eventInDB3);
    }

    @Test(expectedExceptions = DAOException.class)
    public void addEventThatIsNull() {
        eventService.addEvent(null);
    }

    @Test(expectedExceptions = DAOException.class)
    public void addEventWithNullDate() {
        eventService.addEvent(eventWithNullDate);
    }

    @Test(expectedExceptions = DAOException.class)
    public void addEventWithNullName() {
        eventService.addEvent(eventWithNullName);
    }

    @Test(expectedExceptions = DAOException.class)
    public void addEventWithNameAlreadyThere() {
        Event redundantNameEvent = new Event();
        redundantNameEvent.setName("Event 1");
        redundantNameEvent.setDate(LocalDate.now());
        eventService.addEvent(redundantNameEvent);
    }

    @Test
    public void addEvent() {
        Event newEvent = new Event();
        newEvent.setName("New event");
        newEvent.setDate(LocalDate.now());

        eventService.addEvent(newEvent);

        List<Event> allEvents = eventService.findAllEvents();

        assertThat(allEvents).contains(newEvent);
    }

    @Test(expectedExceptions = DAOException.class)
    public void findEventWithNullId() {
        eventService.findEvent(null);
    }

    @Test
    public void findEvent() {
        Event founded = eventService.findEvent(eventInDB1.getId());

        assertThat(founded).isEqualTo(eventInDB1);
    }

    @Test(expectedExceptions = DAOException.class)
    public void editEventThatIsNull() {
        eventService.editEvent(null);
    }

    @Test
    public void editEvent() {
        eventInDB1.setName("Event 1 Updated");
        eventService.editEvent(eventInDB1);

        Event eventDB = eventService.findEvent(eventInDB1.getId());

        assertThat(eventDB).isEqualTo(eventInDB1);
    }

    @Test
    public void removeEvent(){
        eventService.removeEvent(eventInDB3);
        List<Event> allEvents = eventService.findAllEvents();
        assertThat(allEvents).doesNotContain(eventInDB3);
    }

    @Test
    public void findEventsInRange() {
        Event eventInRange = new Event();
        eventInRange.setName("In range");
        eventInRange.setDate(LocalDate.of(2005,1,1));
        eventService.addEvent(eventInRange);

        List<Event> eventsInRange = eventService.findEventsInRange(
                LocalDate.of(2004, 12, 31),
                LocalDate.of(2005,1,2));

        assertThat(eventsInRange).hasSize(1).contains(eventInRange);

    }

    @Test
    public void findEventsByLocation() {
        Event eventInLocation = new Event();
        eventInLocation.setName("Location");
        eventInLocation.setDate(LocalDate.now());
        eventInLocation.setLocation("Brno");
        eventService.addEvent(eventInLocation);

        List<Event> eventsByLocation = eventService.findEventsByLocation("Brno");

        assertThat(eventsByLocation).hasSize(1).contains(eventInLocation);

    }




}

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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


/**
 * @author František Hrdina
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class EventServiceTest extends AbstractTransactionalTestNGSpringContextTests{

    @Mock
    private EventDao eventDao;

    @Autowired
    @InjectMocks
    private EventService eventService;

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
    }

    @Test
    public void findAllEvents() {
        List<Event> expectedEvents = new ArrayList<Event>();
        expectedEvents.add(eventInDB1);
        expectedEvents.add(eventInDB2);
        expectedEvents.add(eventInDB3);

        when(eventDao.findAllEvents()).thenReturn(expectedEvents);
        assertThat(eventService.findAllEvents()).isEqualTo(expectedEvents);
        verify(eventDao, times(1)).findAllEvents();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addEventThatIsNull() {
        doThrow(new IllegalArgumentException()).when(eventDao).addEvent(any());
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
        newEvent.setName("New event to add");
        newEvent.setDate(LocalDate.now());
        eventService.addEvent(newEvent);
        verify(eventDao, times(1)).addEvent(newEvent);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findEventWithNullId() {
        eventService.findEvent(null);
    }

    @Test
    public void findEvent() {
        Event eventToFound = new Event();
        eventToFound.setName("To found");
        eventToFound.setDate(LocalDate.now());
        eventToFound.setId(10L);

        when(eventDao.findEvent(10L)).thenReturn(eventToFound);
        assertThat(eventService.findEvent(10L)).isEqualTo(eventToFound);
        verify(eventDao, times(1)).findEvent(10L);
    }

    @Test(expectedExceptions = DAOException.class)
    public void editEventThatIsNull() {
        eventService.editEvent(null);
    }

    @Test
    public void editEvent() {
        Event eventToUpd = new Event();
        eventToUpd.setName("Event to update");
        eventToUpd.setDate(LocalDate.now());

        eventToUpd.setName("Event 1 Updated");
        eventService.editEvent(eventToUpd);
        verify(eventDao, times(1)).editEvent(eventToUpd);
    }

    @Test
    public void removeEvent(){
        eventService.removeEvent(eventInDB3);
        verify(eventDao, times(1)).removeEvent(eventInDB3);
    }

    @Test
    public void findEventsInRange() {
        Event eventInRange = new Event();
        eventInRange.setName("In range");
        eventInRange.setDate(LocalDate.of(2005,1,1));

        List<Event> expectedEvents = new ArrayList<Event>();
        expectedEvents.add(eventInRange);

        when(eventService.findEventsInRange(
                LocalDate.of(2004, 12, 31),
                LocalDate.of(2005,1,2)))
                .thenReturn(expectedEvents);
        assertThat(eventDao.findEventsInRange(
                LocalDate.of(2004, 12, 31),
                LocalDate.of(2005,1,2)))
                .isEqualTo(expectedEvents);
        verify(eventDao, times(1)).findEventsInRange(
                LocalDate.of(2004, 12, 31),
                LocalDate.of(2005,1,2));
    }

    @Test
    public void findEventsByLocation() {
        Event eventInLocation = new Event();
        eventInLocation.setName("Location");
        eventInLocation.setDate(LocalDate.now());
        eventInLocation.setLocation("Brno");

        List<Event> expectedEvents = new ArrayList<Event>();
        expectedEvents.add(eventInLocation);

        when(eventDao.findEventsByLocation("Brno")).thenReturn(expectedEvents);
        assertThat(eventService.findEventsByLocation("Brno")).isEqualTo(expectedEvents);
        verify(eventDao, times(1)).findEventsByLocation("Brno");
    }
}

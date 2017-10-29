package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * František Hrdina
 *
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EventDaoTest extends AbstractTestNGSpringContextTests{

    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    EntityManager em;

    @Inject
    private EventDao eventDao;

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

        eventDao.addEvent(eventInDB1);
        eventDao.addEvent(eventInDB2);
        eventDao.addEvent(eventInDB3);
      
    }

    @Test
    public void findAllEvents() {
        List<Event> allEvents = eventDao.findAllEvents();

        assertThat(allEvents).hasSize(3).contains(eventInDB1, eventInDB2, eventInDB3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addEventThatIsNull() {
        eventDao.addEvent(null);
    }

    @Test(expectedExceptions = javax.persistence.PersistenceException.class)
    public void addEventWithNullDate() {
        eventDao.addEvent(eventWithNullDate);
    }

    @Test(expectedExceptions = javax.persistence.PersistenceException.class)
    public void addEventWithNullName() {
        eventDao.addEvent(eventWithNullName);
    }

    @Test(expectedExceptions = javax.persistence.PersistenceException.class)
    public void addEventWithNameAlreadyThere() {
        Event redundantNameEvent = new Event();
        redundantNameEvent.setName("Event 1");
        redundantNameEvent.setDate(LocalDate.now());
        eventDao.addEvent(redundantNameEvent);
    }

    @Test
    public void addEvent() {
        Event newEvent = new Event();
        newEvent.setName("New event");
        newEvent.setDate(LocalDate.now());

        eventDao.addEvent(newEvent);

        List<Event> allEvents = eventDao.findAllEvents();

        assertThat(allEvents).contains(newEvent);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findEventWithNullId() {
        eventDao.findEvent(null);
    }

    @Test
    public void findEvent() {
        Event founded = eventDao.findEvent(eventInDB1.getId());

        assertThat(founded).isEqualTo(eventInDB1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void editEventThatIsNull() {
        eventDao.editEvent(null);
    }

    @Test
    public void editEvent() {
        eventInDB1.setName("Event 1 Updated");
        eventDao.editEvent(eventInDB1);

        Event eventDB = eventDao.findEvent(eventInDB1.getId());

        assertThat(eventDB).isEqualTo(eventInDB1);
    }

    @Test
    public void removeEvent(){
        eventDao.removeEvent(eventInDB3);
        List<Event> allEvents = eventDao.findAllEvents();
        assertThat(allEvents).doesNotContain(eventInDB3);
    }

}

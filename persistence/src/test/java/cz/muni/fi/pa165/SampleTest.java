
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    EntityManager em;

    @Inject
    private EventDao eventDao;

    @Test
    public void eventTest1() {
        Event e = new Event();
        e.setDate(LocalDate.now());
        e.setName("test");

        eventDao.addEvent(e);

        List<Event> allEvents = eventDao.findAllEvents();
        assertThat(allEvents).hasSize(1);
        assertThat(allEvents.contains(e));
    }

/*    @Test
    public void eventTest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event e = new Event();
        e.setDate(LocalDate.now());
        e.setName("test");
        em.persist(e);
        em.getTransaction().commit();
        em.close();
        em = emf.createEntityManager();
        Event ev = em.find(Event.class, e.getId());
        assertEquals(e, ev);
    }*/
}
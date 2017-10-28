
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entities.Event;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.testng.AssertJUnit.assertEquals;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class SampleTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void eventTest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event e = new Event();
        em.persist(e);
        em.getTransaction().commit();
        em.close();
        em = emf.createEntityManager();
        Event ev = em.find(Event.class, e.getId());
        assertEquals(e, ev);
    }
}
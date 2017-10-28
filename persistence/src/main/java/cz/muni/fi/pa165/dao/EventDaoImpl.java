package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin Kocisky
 */

@Repository
public class EventDaoImpl implements EventDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addEvent(Event e) {
        em.persist(e);
    }

    @Override
    public void removeEvent(Event e) {
        em.remove(e);
    }

    @Override
    public void editEvent(Event e) {
        em.merge(e);
    }

    @Override
    public Event findEvent(Long id) {
        return em.find(Event.class, id);
    }

    @Override
    public List<Event> findAllEvents() {
        return em.createQuery("select e from Event e", Event.class).getResultList();
    }

}

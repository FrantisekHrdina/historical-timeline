package cz.muni.fi.pa165.dao;

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
    public void create(Event e) {
        em.persist(e);
    }

    /**
     * Delete group g from the database.
     * @param g
     */
    @Override
    public void delete(Event e) {
        em.remove(e);
    }

    @Override
    public void update(Event e) {
        em.merge(e);
    }

    /**
     * Find group by id and return an object.
     * @param id of the group
     * @return
     */
    @Override
    public Event findById(Long id) {
        return em.find(Event.class, id);
    }

    /**
     * Find group by name and return an object.
     * @param name of the group
     * @return
     */
    @Override
    public Event findByName(String name) {
        return em.createQuery("select e FROM Event e where e.name like :name ", Event.class).setParameter("name", name).getFirstResult();
    }

    /**
     * Find all groups.
     * @return
     */
    @Override
    public List<Event> findAll() {
        return em.createQuery("select e from Event e", Event.class).getResultList();
    }

}

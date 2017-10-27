package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tibor Bujdoš
 */
public class TimelineDaoImpl implements TimelineDao{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addTimeline(Timeline timeline, SeminarGroup seminarGroup) {
        seminarGroup.getTimelines().add(timeline);
        em.persist(timeline);
    }

    @Override
    public void removeTimeline(Timeline timeline) {
        em.remove(timeline);
    }

    @Override
    public void editTimeline(Timeline timeline) {
        em.merge(timeline);
    }

    @Override
    public Timeline findTimeline(Long id) {
        return em.find(Timeline.class, id);
    }

    @Override
    public List<Timeline> findAllTimelines() {
        return em.createQuery("select t from Timeline t", Timeline.class)
                .getResultList();
    }
    
}

package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tibor BujdoĹˇ
 */
@Repository
public class TimelineDaoImpl implements TimelineDao{
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private SeminarGroupDao smd;

    @Override
    public void addTimeline(Timeline timeline, SeminarGroup seminarGroup) {
        seminarGroup.getTimelines().add(timeline);
        smd.editGroup(seminarGroup);
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


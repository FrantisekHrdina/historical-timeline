package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for TimelineDao, for each method one test.
 * @author Martin Kocisky
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TimelineDaoTest extends AbstractTestNGSpringContextTests {
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TimelineDao timelineDao;
    
    @Autowired
    private SeminarGroupDao seminarGroupDao;

    private Timeline firstTimeline;
    private Timeline secondTimeline;

    private SeminarGroup firstSeminarGroup;
    private SeminarGroup secondSeminarGroup;

    @BeforeMethod
    public void setUp() {
        firstTimeline = new Timeline();
        firstTimeline.setName("Ice Age");

        secondTimeline = new Timeline();
        secondTimeline.setName("Bronze Age");

        firstSeminarGroup = new SeminarGroup();
        firstSeminarGroup.setName("Loosing Group");

        secondSeminarGroup = new SeminarGroup();
        secondSeminarGroup.setName("Winning Group");
    }

    /**
     * Attempting to persist null.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addTimelineTest_timelineNull() {
        timelineDao.addTimeline(null);
    }

    /**
     * Test adding timeline with missing name.
     */
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void addTimelineTest_IncorrectTimeline() {
        firstTimeline.setName(null);
        timelineDao.addTimeline(firstTimeline);
    }

    /**
     * Add correct timeline.
     */
    @Test
    public void addTimelineTest() {
        timelineDao.addTimeline(firstTimeline);

        Timeline fromDB = timelineDao.findTimeline(firstTimeline.getId());
        assertThat(firstTimeline.getId()).isEqualTo(fromDB.getId());
    }

    /**
     * Attempting to remove null.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeTimelineTest_nullTimeline() {
        timelineDao.removeTimeline(null);
    }

    /**
     * Try to remove timeline which is not in DB.
     */
    @Test
    public void removeTimelineTest_timelineNotInDb() {  
        timelineDao.addTimeline(secondTimeline);

        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(1);

        timelineDao.removeTimeline(firstTimeline);

        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(1);

    }

    /**
     * Remove timeline.
     */
    @Test
    public void removeTimelineTest() {
        timelineDao.addTimeline(firstTimeline);
        timelineDao.addTimeline(secondTimeline);
        
        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(2);

        timelineDao.removeTimeline(secondTimeline);
        
        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(1);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void editTimelineTest_nullTimeline() {
        timelineDao.editTimeline(null);
    }

    @Test
    public void editTimelineTest() {
        timelineDao.addTimeline(firstTimeline);
        firstTimeline.setName("Stone Age");
        timelineDao.editTimeline(firstTimeline);

        assertThat(timelineDao.findTimeline(firstTimeline.getId()).getName()).isEqualTo(firstTimeline.getName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findTimelineTest_nullTimeline() {
        timelineDao.findTimeline(null);
    }

    @Test
    public void findTimelineTest() {
        timelineDao.addTimeline(firstTimeline);

        assertThat(timelineDao.findTimeline(firstTimeline.getId())).isEqualTo(firstTimeline);
    }

    @Test
    public void findAllTimelinesTest() {
        timelineDao.addTimeline(firstTimeline);
        timelineDao.addTimeline(secondTimeline);

        assertThat(timelineDao.findAllTimelines()).containsExactlyInAnyOrder(firstTimeline, secondTimeline);
    }
}

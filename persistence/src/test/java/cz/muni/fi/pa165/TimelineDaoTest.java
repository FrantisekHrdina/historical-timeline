package cz.muni.fi.pa165;

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

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for TimelineDao, for each method one test.
 * @author Martin Kocisky
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TimelineDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private TimelineDao timelineDao;

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
    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void addTimelineTest_timelineNull() {
        timelineDao.addTimeline(null, firstSeminarGroup);
    }


    /**
     * Failed to add timeline to a seminar group;
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void addTimelineTest_seminarGroupNull() {
        timelineDao.addTimeline(firstTimeline, null);
    }

    /**
     * Test adding timeline with missing name.
     */
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void addTimelineTest_IncorrectTimeline() {
        firstTimeline.setName(null);
        timelineDao.addTimeline(firstTimeline, firstSeminarGroup);
    }

    /**
     * Add correct timeline.
     */
    @Test
    public void addTimelineTest() {
        timelineDao.addTimeline(firstTimeline, firstSeminarGroup);

        Timeline fromDB = timelineDao.findTimeline(firstTimeline.getId());
        assertThat(firstTimeline.getId()).isEqualTo(fromDB.getId());
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void removeTimelineTest_timelineNull() {
        timelineDao.removeTimeline(null);
    }

    @Test
    public void removeTimelineTest_timelineNotInDb() {
        timelineDao.addTimeline(secondTimeline, secondSeminarGroup);

        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(1);

        timelineDao.removeTimeline(firstTimeline);

        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(1);

    }

    @Test
    public void removeTimelineTest() {
        timelineDao.addTimeline(firstTimeline, firstSeminarGroup);
        timelineDao.addTimeline(secondTimeline, secondSeminarGroup);

        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(2);

        timelineDao.removeTimeline(secondTimeline);

        assertThat(timelineDao.findAllTimelines().size()).isEqualTo(1);
    }

    @Test
    public void editTimelineTest() {


    }

    @Test
    public void findTimelineTest() {

    }

    @Test
    public void findAllTimelinesTest() {

    }
}

package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.exception.DAOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Martin Kocisky, 421131
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TimelineServiceTest {

    @Mock
    private TimelineDao timelineDao;

    @Mock
    private SeminarGroupDao seminarGroupDao;

    @Mock
    private EventDao eventDao;

    @Autowired
    @InjectMocks
    private TimelineServiceImpl timelineService;

    private Timeline testTimeline;
    private Timeline testTimeline2;
    private Timeline testTimeline3;

    private SeminarGroup testSeminarGroup;
    private SeminarGroup testSeminarGroup2;

    private Event testEvent;
    private Event testEvent2;

    @Before
    public void setUp() {
        prepareTestTimelines();
        prepareTestSeminarGroup();
        prepareTestEvent();
        prepareMockBehaviour();
    }

    public void prepareTestTimelines(){
        testTimeline = new Timeline();
        testTimeline.setName("Ice Age");
        testTimeline.setId(1L);

        testTimeline2 = new Timeline();
        testTimeline2.setId(2L);

        testTimeline3 = new Timeline();
        testTimeline3.setName("Stone Age");
        testTimeline3.setId(3L);
    }

    public void prepareTestSeminarGroup(){
        testSeminarGroup = new SeminarGroup();
        testSeminarGroup.setName("Class #1");
        testSeminarGroup.setId(1L);

        testSeminarGroup.setTimelines(Arrays.asList(testTimeline3));
        testTimeline3.setSeminarGroup(testSeminarGroup);

        testSeminarGroup2 = new SeminarGroup();
        testSeminarGroup2.setName("Class #2");
        testSeminarGroup2.setId(2L);
    }

    public void prepareTestEvent(){
        testEvent = new Event();
        testEvent.setName("Today a bear froze.");
        testEvent.setDescription("This poor bear froze to death so suddenly.");
        testEvent.setLocation("Europe");
        testEvent.setId(1L);

        testTimeline3.setEvents(Arrays.asList(testEvent));
        testEvent.setTimelines(Arrays.asList(testTimeline3));

        testEvent2 = new Event();
        testEvent2.setName("Today a bear unfroze.");
        testEvent2.setDescription("This lucky bear woke up so suddenly.");
        testEvent2.setLocation("Alaska");
        testEvent2.setId(2L);
    }

    public void prepareMockBehaviour() {

        doAnswer((InvocationOnMock invocation) -> {
            Object argument = invocation.getArguments()[0];
            if (argument == null) {
                throw new NullPointerException("Argument is null.");
            }

            Timeline timeline = (Timeline) argument;
            if (timeline.getName() == null) {
                throw new NullPointerException("Title is null.");
            }

            timeline.setId(1L);
            return null;

        }).when(timelineDao).addTimeline(any(Timeline.class));

        when(timelineDao.findTimeline(1L)).thenReturn(testTimeline);
        when(timelineDao.findTimeline(null)).thenThrow(new NullPointerException());
        when(timelineDao.findTimeline(7L)).thenThrow(new NullPointerException());
        when(timelineDao.findAllTimelines()).thenReturn(Arrays.asList(testTimeline, testTimeline2));

        when(timelineDao.findTimeline(3L)).thenReturn(testTimeline3);
        when(seminarGroupDao.findGroup(1L)).thenReturn(testSeminarGroup);
        when(eventDao.findEvent(1L)).thenReturn(testEvent);
        when(eventDao.findEvent(2L)).thenReturn(testEvent2);

    }

    @Test
    public void createTimelineTest() {
        timelineService.createTimeline(testTimeline);
        verify(timelineDao).addTimeline(testTimeline);
    }

    @Test(expected = DataAccessException.class)
    public void createTimelineTest_NoName() {
        timelineService.createTimeline(testTimeline2);
    }

    @Test(expected = DataAccessException.class)
    public void createTimelineTest_null() {
        timelineService.createTimeline(null);
    }

    @Test
    public void addCommentTest() {
        String comment = "Ice Age was so icy.";

        timelineService.addComment(1L, comment);
        verify(timelineDao).findTimeline(1L);

        Assert.assertEquals(testTimeline.getComments(), Arrays.asList(comment));

        verify(timelineDao).editTimeline(testTimeline);
    }

    @Test
    public void setSeminarGroupToTimeline() {
        timelineService.setSeminarGroupToTimeline(1L, 1L);
        verify(timelineDao).findTimeline(1L);
        verify(seminarGroupDao).findGroup(1L);

        Assert.assertEquals(testTimeline.getSeminarGroup(), testSeminarGroup);
        Assert.assertEquals(testSeminarGroup.getTimelines(), Arrays.asList(testTimeline3, testTimeline));

        verify(timelineDao).editTimeline(testTimeline);
        verify(seminarGroupDao).editGroup(testSeminarGroup);
    }

    @Test
    public void removeSeminarGroupFromTimeline() {
        timelineService.removeSeminarGroupFromTimeline(3L);
        verify(timelineDao).findTimeline(3L);

        Assert.assertEquals(testTimeline3.getSeminarGroup(), null);
        Assert.assertEquals(testSeminarGroup.getTimelines().size(), 0);

        verify(timelineDao).editTimeline(testTimeline3);
        verify(seminarGroupDao).editGroup(testSeminarGroup);
    }

    @Test
    public void addEventToTimeline() {
        timelineService.addEventToTimeline(1L, 2L);
        verify(timelineDao).findTimeline(1L);
        verify(eventDao).findEvent(2L);

        Assert.assertEquals(testTimeline.getEvents(), Arrays.asList(testEvent2));
        Assert.assertEquals(testEvent2.getTimelines(), Arrays.asList(testTimeline));

        verify(timelineDao).editTimeline(testTimeline);
        verify(eventDao).editEvent(testEvent2);
    }

    @Test
    public void removeEventFromTimeline() {
        timelineService.removeEventFromTimeline(3L, 1L);
        verify(timelineDao).findTimeline(3L);
        verify(eventDao).findEvent(1L);

        Assert.assertEquals(testTimeline3.getEvents().size(), 0);
        Assert.assertEquals(testEvent.getTimelines().size(), 0);

        verify(timelineDao).editTimeline(testTimeline3);
        verify(eventDao).editEvent(testEvent);
    }

    @Test
    public void getTimelineByIdTest() {
        Assert.assertEquals(timelineDao.findTimeline(1L), testTimeline);
        verify(timelineDao).findTimeline(1L);
    }

    @Test(expected = DAOException.class)
    public void getTimelineByIdTest_NotFound() {
        timelineService.getTimelineById(7L);
        verify(timelineDao).findTimeline(7L);
    }

    @Test(expected = DAOException.class)
    public void getTimelineByIdTest_null() {
        timelineService.getTimelineById(null);
        verify(timelineDao).findTimeline(null);
    }

    @Test
    public void getAllTimelinesTest() {
        Assert.assertEquals(timelineDao.findAllTimelines(), Arrays.asList(testTimeline, testTimeline2));
        verify(timelineDao).findAllTimelines();
    }

    @Test
    public void deleteTimelineTest() {
        timelineService.deleteTimeline(testTimeline.getId());
        verify(timelineDao).removeTimeline(testTimeline);
    }

}

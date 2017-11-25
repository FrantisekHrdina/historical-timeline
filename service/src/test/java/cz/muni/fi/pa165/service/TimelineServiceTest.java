package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TimelineServiceTest {

    @Mock
    private TimelineDao timelineDao;

    @Mock
    private SeminarGroupDao seminarGroupDao;

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private TimelineService timelineService = new TimelineServiceImpl();

    private Timeline testTimeline;
    private Timeline testTimeline2;
    private Timeline testTimeline3;

    private SeminarGroup testSeminarGroup;

    private Event testEvent;

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
    }

    public void prepareTestEvent(){
        testEvent = new Event();
        testEvent.setName("Today a bear froze.");
        testEvent.setDescription("This poor bear froze to death so suddenly.");
        testEvent.setLocation("Europe");
        testEvent.setId(1L);

        testTimeline3.setEvents(Arrays.asList(testEvent));
        testEvent.setTimelines(Arrays.asList(testTimeline3));
    }

    public void prepareMockBehaviour() {

        // create behaviour
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



        // find behaviour
        when(timelineDao.findTimeline(1L)).thenReturn(testTimeline);
        when(timelineDao.findTimeline(null)).thenThrow(new NullPointerException());
        when(timelineDao.findTimeline(7L)).thenThrow(new NullPointerException());
        when(timelineDao.findAllTimelines()).thenReturn(Arrays.asList(testTimeline, testTimeline2));

        // logic
        when(timelineDao.findTimeline(3L)).thenReturn(testTimeline3);
        when(seminarGroupDao.findGroup(1L)).thenReturn(testSeminarGroup);
        when(eventDao.findEvent(1L)).thenReturn(testEvent);

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
//        String comment = "Ice Age was so icy.";
//        String comment2 = "Ice Age was too long ago.";
//
//        Long id = timelineService.createTimeline(testTimeline);
//
//        // check one comment
//        timelineService.addComment(id, comment);
//
//        Timeline timeline = timelineService.getTimelineById(id);
//
//        Assert.assertEquals(timeline.getComments().size(), 1);
//        Assert.assertEquals(timeline.getComments().get(0), comment);
//
//        // check comment added to the end
//        timelineService.addComment(id, comment2);
//        timeline = timelineService.getTimelineById(id);
//
//        Assert.assertEquals(timeline.getComments().size(), 2);
//        Assert.assertEquals(timeline.getComments().get(timeline.getComments().size() - 1), comment2);
    }


    ///////////////////////////////////// make sure these methods that manipulate multiple Dao work
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
        timelineService.addEventToTimeline(1L, 1L);
        verify(timelineDao).findTimeline(1L);
        verify(eventDao).findEvent(1L);

        Assert.assertEquals(testTimeline.getEvents(), Arrays.asList(testEvent));
        Assert.assertEquals(testEvent.getTimelines(), Arrays.asList(testTimeline));

        Assert.assertEquals(testTimeline3.getEvents().size(), 1);
        Assert.assertEquals(testEvent.getTimelines().size(), 1);

        verify(timelineDao).editTimeline(testTimeline);
        verify(eventDao).editEvent(testEvent);
    }

    @Test
    public void removeEventFromTimeline() {
        timelineService.removeEventFromTimeline(3L, 1L);
        verify(timelineDao).findTimeline(3L);
        verify(eventDao).findEvent(1L);

        Assert.assertEquals(testTimeline3.getEvents().size(), 0);
        Assert.assertEquals(testEvent.getTimelines().size(), 0);

        verify(timelineDao).editTimeline(testTimeline);
        verify(eventDao).editEvent(testEvent);
    }
    /////////////////////////////////////

    @Test
    public void getTimelineByIdTest() {
        Assert.assertEquals(timelineDao.findTimeline(1L), testTimeline);
        verify(timelineDao).findTimeline(1L);
    }

    @Test(expected = DataAccessException.class)
    public void getTimelineByIdTest_NotFound() {
        timelineDao.findTimeline(7L);
    }

    @Test(expected = DataAccessException.class)
    public void getTimelineByIdTest_null() {
        timelineDao.findTimeline(null);
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

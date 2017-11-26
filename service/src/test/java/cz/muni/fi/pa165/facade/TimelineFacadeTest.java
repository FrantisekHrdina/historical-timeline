package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.service.TimelineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Martin Kocisky, 421131
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TimelineFacadeTest {

    @Mock
    private TimelineService timelineService;

    @InjectMocks
    private TimelineFacade timelineFacade = new TimelineFacadeImpl();

    private TimelineDTO testTimelineDTO;
    private Timeline testTimeline;

    @Before
    public void setUp() {
        prepareEntities();
        prepareMockBahaviour();
    }

    public void prepareEntities() {
        testTimelineDTO = new TimelineDTO();
        testTimelineDTO.setName("Ice Age");

        testTimeline = new Timeline();
        testTimeline.setId(1L);
        testTimeline.setName("Bronze Age");
    }

    public void prepareMockBahaviour() {
        when(timelineService.getTimelineById(1L)).thenReturn(testTimeline);
        when(timelineService.getAllTimelines()).thenReturn(Arrays.asList(testTimeline));
    }

    @Test
    public void createTimelineTest() {
        timelineFacade.createTimeline(testTimelineDTO);
        verify(timelineService).createTimeline(any(Timeline.class));
    }

    @Test
    public void addCommentTest() {
        String comment = "Apples are violet.";
        timelineFacade.addComment(1L, comment);
        verify(timelineService).addComment(1L, comment);
    }

    @Test
    public void addSeminarGroupTest() {
        timelineFacade.addSeminarGroup(1L, 1L);
    }

    @Test
    public void removeSeminarGroupTest() {
        timelineFacade.removeSeminarGroup(1L);
    }

    @Test
    public void addEventTest() {
        timelineFacade.addEvent(1L,1L);
        verify(timelineService).addEventToTimeline(1L, 1L);
    }

    @Test
    public void removeEventTest() {
        timelineFacade.removeEvent(1L, 1L);
        verify(timelineService).removeEventFromTimeline(1L, 1L);
    }

    @Test
    public void getTimelineByIdTest() {
        TimelineDTO timelineDTO = timelineFacade.getTimelineById(1L);
        verify(timelineService).getTimelineById(1L);

        assertThat(timelineDTO.getId()).isEqualTo(1L);
        assertThat(timelineDTO.getName()).contains("Bronze Age");
    }

    @Test
    public void getAllTimelinesTest() {
        List<TimelineDTO> timelineDTOList = timelineFacade.getAllTimelines();
        verify(timelineService).getAllTimelines();
        Assert.assertEquals(timelineDTOList.size(), 1);
    }

    @Test
    public void deleteTimelineTest() {
        timelineFacade.deleteTimeline(1L);
        verify(timelineService).deleteTimeline(1L);
    }
}

package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.service.TimelineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

import java.sql.Time;
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

    public void setUp() {
        testTimelineDTO = new TimelineDTO();
        testTimelineDTO.setName("Ice Age");
    }

    public void prepareBahaviour() {
        when(timelineService.createTimeline(any(Timeline.class))).thenReturn(1L);
    }

    @Test
    public void createTimelineTest() {
        Long id = timelineFacade.createTimeline(testTimelineDTO);
        verify(timelineService).createTimeline(any(Timeline.class));
        assertThat(id).isEqualTo(1L);
    }

    @Test
    public void addCommentTest() {
    }

    @Test
    public void addSeminarGroupTest() {
    }

    @Test
    public void removeSeminarGroupTest() {
    }

    @Test
    public void addEventTest() {
    }

    @Test
    public void removeEventTest() {
    }

    @Test
    public void getTimelineByIdTest() {
    }

    @Test
    public void getAllTimelinesTest() {
    }

    @Test
    public void deleteTimelineTest() {
    }
}

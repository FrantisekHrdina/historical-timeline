package cz.muni.fi.pa165.mapping;


import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author František Hrdina
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private List<Event> events = new ArrayList<Event>();
    private List<Timeline> timelines = new ArrayList<Timeline>();

    @BeforeMethod
    public void createEvents(){
        Event firstEvent = new Event();
        firstEvent.setName("First");
        firstEvent.setDescription("Description of first");
        firstEvent.setLocation("First location");
        firstEvent.setDate(LocalDate.of(1, Month.JANUARY, 1));

        Event secondEvent = new Event();
        secondEvent.setName("Second");
        secondEvent.setDescription("Description of second");
        secondEvent.setLocation("Second location");
        secondEvent.setDate(LocalDate.of(1999, Month.JANUARY, 31));


        Event thirdEvent = new Event();
        thirdEvent.setName("Third");
        thirdEvent.setDescription("Description of third");
        thirdEvent.setLocation("Third location");
        thirdEvent.setDate(LocalDate.now());

        events.add(firstEvent);
        events.add(secondEvent);
        events.add(thirdEvent);

        Timeline timeline = new Timeline();
        timeline.setName("Test");
        timelines.add(timeline);


    }

    @Test
    public void mapEvents(){
        List<EventDTO> mappedEvents = beanMappingService.mapTo(events, EventDTO.class);
        assertThat(mappedEvents).hasSize(events.size());
    }

    @Test
    public void mapTimelines(){
        List<TimelineDTO> mappedTimelines = beanMappingService.mapTo(timelines, TimelineDTO.class);
        assertThat(mappedTimelines).hasSize(timelines.size());

    }

}

package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.SeminarGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import cz.muni.fi.pa165.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private UserService userService;

    @Autowired
    private SeminarGroupService seminarGroupService;

    @SuppressWarnings("unused")
    @Override
    public void loadData() throws IOException {
        sampleData();
    }

    private void sampleData() {
        Timeline timeline = sampleTimeline("Ice Age");
        Event event = sampleEvent("Extinction", "World", "Extinction of all.");
        SeminarGroup seminarGroup = sampleSeminarGroup("Group #1");

        timelineService.addComment(timeline.getId(), "This is a boring period.");
        timelineService.addEventToTimeline(timeline.getId(), event.getId());
//        timelineService.setSeminarGroupToTimeline(timeline.getId(), seminarGroup.getId());
    }

    private SeminarGroup sampleSeminarGroup(String name) {
        SeminarGroup seminarGroup = new SeminarGroup();
        seminarGroup.setName(name);
        seminarGroupService.saveGroup(seminarGroup);
        return seminarGroup;
    }

    private Event sampleEvent(String name, String location, String description) {
        Event event = new Event();
        event.setName(name);
        event.setLocation(location);
        event.setDescription(description);
        event.setDate(LocalDate.now());
        eventService.addEvent(event);
        return event;
    }

    private Timeline sampleTimeline(String name) {
        Timeline timeline = new Timeline();
        timeline.setName(name);
        timelineService.createTimeline(timeline);
        return timeline;
    }
}

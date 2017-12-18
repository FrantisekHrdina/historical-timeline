package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.entities.User;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        SeminarGroup seminarGroup2 = sampleSeminarGroup("Group #2");
        SeminarGroup seminarGroup3 = sampleSeminarGroup("Group #3");
        SeminarGroup seminarGroup4 = sampleSeminarGroup("Group #4");
        
        User student = sampleStudent("Student", "1", "s1@skola.cz");
        User teacher = sampleTeacher("Teacher", "1", "t1@skola.cz");
        
        MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update("1234".getBytes());
	        String encryptedString = new String(messageDigest.digest());
	        student.setPasswordHash(encryptedString);
	        messageDigest.update("4321".getBytes());
	        encryptedString = new String(messageDigest.digest());
	        teacher.setPasswordHash(encryptedString);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        student.addSeminarGroup(seminarGroup);
        student.addSeminarGroup(seminarGroup2);
        student.addSeminarGroup(seminarGroup3);
        student.addSeminarGroup(seminarGroup4);
        userService.editUser(student);

        timelineService.addComment(timeline.getId(), "This is a boring period.");
        timelineService.addEventToTimeline(timeline.getId(), event.getId());
        timelineService.setSeminarGroupToTimeline(timeline.getId(), seminarGroup.getId());


        Timeline timeline1 = sampleTimeline("Timeline1");
        //SeminarGroup seminarGroup1 = sampleSeminarGroup("Seminargroup-Timeline1");

        Event event1 = sampleEvent("Event1-Timeline1", "Event1-Timeline1-Location", "Event1-Timeline1-Desc");
        Event event2 = sampleEvent("Event2-Timeline1", "Event2-Timeline1-Location", "Event2-Timeline1-Desc");
        Event event3 = sampleEvent("Event3-Timeline1", "Event3-Timeline1-Location", "Event3-Timeline1-Desc");

        timelineService.addEventToTimeline(timeline1.getId(), event1.getId());
        timelineService.addEventToTimeline(timeline1.getId(), event2.getId());
        timelineService.addEventToTimeline(timeline1.getId(), event3.getId());

        Timeline timeline2 = sampleTimeline("Timeline2");
        //SeminarGroup seminarGroup1 = sampleSeminarGroup("Seminargroup-Timeline1");

        Event event12 = sampleEvent("Event1-Timeline2", "Event1-Timeline2-Location", "Event1-Timeline2-Desc");
        Event event22 = sampleEvent("Event2-Timeline2", "Event2-Timeline2-Location", "Event2-Timeline2-Desc");
        Event event32 = sampleEvent("Event3-Timeline2", "Event3-Timeline2-Location", "Event3-Timeline2-Desc");

        timelineService.addEventToTimeline(timeline2.getId(), event12.getId());
        timelineService.addEventToTimeline(timeline2.getId(), event22.getId());
        timelineService.addEventToTimeline(timeline2.getId(), event32.getId());

        Timeline timeline3 = sampleTimeline("Timeline3");
        //SeminarGroup seminarGroup1 = sampleSeminarGroup("Seminargroup-Timeline1");

        Event event13 = sampleEvent("Event1-Timeline3", "Event1-Timeline3-Location", "Event1-Timeline3-Desc");
        Event event23 = sampleEvent("Event2-Timeline3", "Event2-Timeline3-Location", "Event2-Timeline3-Desc");
        Event event33 = sampleEvent("Event3-Timeline3", "Event3-Timeline3-Location", "Event3-Timeline3-Desc");

        timelineService.addEventToTimeline(timeline3.getId(), event13.getId());
        timelineService.addEventToTimeline(timeline3.getId(), event23.getId());
        timelineService.addEventToTimeline(timeline3.getId(), event33.getId());

        timelineService.setSeminarGroupToTimeline(timeline1.getId(), seminarGroup4.getId());
        timelineService.setSeminarGroupToTimeline(timeline2.getId(), seminarGroup3.getId());
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
    
    private User sampleStudent(String forename, String surname, String email) {
        User user = new User();
        user.setForename(forename);
        user.setSurname(surname);
        user.setEmail(email);
        user.setIsTeacher(false);
        userService.addUser(user);
        return user;
    }
    
    private User sampleTeacher(String forename, String surname, String email) {
        User user = new User();
        user.setForename(forename);
        user.setSurname(surname);
        user.setEmail(email);
        user.setIsTeacher(true);
        userService.addUser(user);
        return user;
    }
}

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

        
        User student = sampleStudent("Student", "1", "s1@skola.cz");
        User teacher = sampleTeacher("Teacher", "1", "t1@skola.cz");

        /**
         * Star of Example
         */
        SeminarGroup groupExample = sampleSeminarGroup("History Group");
        Timeline timelineExample = sampleTimeline("History Timeline");
        student.addSeminarGroup(groupExample);
        userService.editUser(student);

        timelineService.setSeminarGroupToTimeline(timelineExample.getId(), groupExample.getId());

        Event startWWI = new Event();
        startWWI.setName("Start of WW I");
        startWWI.setDate(LocalDate.of(1914,7,28));
        startWWI.setLocation("Sarajevo");
        startWWI.setDescription("It was start of WW I ......");
        startWWI.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEyFcYQnSJl2MLf1u3BDWqiv_0UftQ9BLgafU2HltkA8yVFtIfdQ");

        Event endWWI = new Event();
        endWWI.setName("End of WW I");
        endWWI.setDate(LocalDate.of(1918,11,11));
        endWWI.setLocation("Compiègne");
        endWWI.setDescription("It was end of WW I ......");
        endWWI.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/World_War_I_photographs_-_NARA_-_285377.jpg/220px-World_War_I_photographs_-_NARA_-_285377.jpg");

        Event startWWII = new Event();
        startWWII.setName("Start of WW II");
        startWWII.setDate(LocalDate.of(1939,9,1));
        startWWII.setLocation("Poland");
        startWWII.setDescription("Invasion to Poland..");
        startWWII.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Spotkanie_Sojusznik%C3%B3w.jpg/250px-Spotkanie_Sojusznik%C3%B3w.jpg");

        Event endWWII = new Event();
        endWWII.setName("End of WW II");
        endWWII.setDate(LocalDate.of(1945,9,2));
        endWWII.setLocation("Japan");
        endWWII.setDescription("Capitulation of Japan");
        endWWII.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Hiroshima_aftermath.jpg/290px-Hiroshima_aftermath.jpg");

        Event atomBomb = new Event();
        atomBomb.setName("Atomic Bomb");
        atomBomb.setLocation("Hiroshima");
        atomBomb.setDate(LocalDate.of(1945,8,6));
        atomBomb.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Atomic_bombing_of_Japan.jpg/300px-Atomic_bombing_of_Japan.jpg");

        Event czechRep = new Event();
        czechRep.setName("Creation of Czech Republic");
        czechRep.setLocation("Central Europe");
        czechRep.setDate(LocalDate.of(1993,1,1));
        czechRep.setDescription("Creation of CZ ....");
        czechRep.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Coat_of_arms_of_the_Czech_Republic.svg/50px-Coat_of_arms_of_the_Czech_Republic.svg.png");


        Event czechslovakia = new Event();
        czechslovakia.setName("Creation of Czechoslovakia");
        czechslovakia.setLocation("Central Europe");
        czechslovakia.setDate(LocalDate.of(1918,10,28));
        czechslovakia.setDescription("Creation of ČSR ....");
        czechslovakia.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Flag_of_the_Czech_Republic.svg/125px-Flag_of_the_Czech_Republic.svg.png");

        Event gagarin = new Event();
        gagarin.setName("First man in space");
        gagarin.setDate(LocalDate.of(1961,5,12));
        gagarin.setLocation("Space");
        gagarin.setDescription("..........................");
        gagarin.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Yuri-Gagarin-1961-Helsinki-crop.jpg/220px-Yuri-Gagarin-1961-Helsinki-crop.jpg");

        Event moon = new Event();
        moon.setName("Fist man on the Moon");
        moon.setLocation("The moon");
        moon.setDate(LocalDate.of(1969,7,20));
        moon.setDescription("That's one small step for man one giant leap for mankind");
        moon.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTe0OMT8o9I8wFyTBBwXrLXAcr2q0qvNi4hJ11xygAdHy_f3fzY");


        eventService.addEvent(startWWI);
        eventService.addEvent(startWWII);
        eventService.addEvent(endWWI);
        eventService.addEvent(endWWII);
        eventService.addEvent(moon);
        eventService.addEvent(gagarin);
        eventService.addEvent(czechRep);
        eventService.addEvent(czechslovakia);
        eventService.addEvent(atomBomb);

        timelineService.addEventToTimeline(timelineExample.getId(), startWWI.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), startWWII.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), endWWI.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), endWWII.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), moon.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), gagarin.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), czechRep.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), czechslovakia.getId());
        timelineService.addEventToTimeline(timelineExample.getId(), atomBomb.getId());

        timelineService.addComment(timelineExample.getId(), "CooL!");
        timelineService.addComment(timelineExample.getId(), "OMG!");
        timelineService.addComment(timelineExample.getId(), "Nice, bro!");
        timelineService.addComment(timelineExample.getId(), "Interesting");

        /**
         * End of Example
         */

        Timeline timeline = sampleTimeline("Ice Age");
        Event event = sampleEvent("Extinction", "World", "Extinction of all.");
        SeminarGroup seminarGroup = sampleSeminarGroup("Group #1");
        SeminarGroup seminarGroup2 = sampleSeminarGroup("Group #2");
        SeminarGroup seminarGroup3 = sampleSeminarGroup("Group #3");
        SeminarGroup seminarGroup4 = sampleSeminarGroup("Group #4");



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
        
/*        student.addSeminarGroup(seminarGroup2);
        student.addSeminarGroup(seminarGroup);
        student.addSeminarGroup(seminarGroup4);
        student.addSeminarGroup(groupExample);
        userService.editUser(student);*/

        timelineService.addComment(timeline.getId(), "This is a boring period.");
        timelineService.addEventToTimeline(timeline.getId(), event.getId());
        timelineService.setSeminarGroupToTimeline(timeline.getId(), seminarGroup.getId());


        Timeline timeline1 = sampleTimeline("Timeline1");
        //SeminarGroup seminarGroup1 = sampleSeminarGroup("Seminargroup-Timeline1");

        Event event1 = sampleEvent("Event1-Timeline1", "Event1-Timeline1-Location", "Event1-Timeline1-Desc");
        Event event2 = sampleEvent("Event2-Timeline1", "Event2-Timeline1-Location", "Event2-Timeline1-Desc");
        Event event3 = sampleEvent("Event3-Timeline1", "Event3-Timeline1-Location", "Event3-Timeline1-Desc");

        Event beforeChrist = new Event();
        beforeChrist.setLocation("bla");
        beforeChrist.setDescription("bla bla");
        beforeChrist.setName("before christ");
        beforeChrist.setDate(LocalDate.of(-200,1,1));

        eventService.addEvent(beforeChrist);

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


        //Testing View Events in Timeline
        Timeline timelineTestView = sampleTimeline("TestView");
        Event first = new Event();
        first.setName("First");
        first.setDate(LocalDate.of(2000,1,1));
        first.setLocation("somewhere");
        first.setDescription("some Desc");
        first.setImage("https://goo.gl/ipe3Eo");

        Event second = new Event();
        second.setName("Second");
        second.setDate(LocalDate.of(2005,1,1));
        second.setLocation("somewhere");
        second.setDescription("some Desc");
        second.setImage("https://goo.gl/r3NzTG");

        Event third = new Event();
        third.setName("Third");
        third.setDate(LocalDate.of(2010,1,1));
        third.setLocation("somewhere");
        third.setDescription("some Desc");
        third.setImage("https://goo.gl/KuHjEz");

        eventService.addEvent(first);
        eventService.addEvent(third);
        eventService.addEvent(second);

        timelineService.addEventToTimeline(timelineTestView.getId(), first.getId());
        timelineService.addEventToTimeline(timelineTestView.getId(), third.getId());
        timelineService.addEventToTimeline(timelineTestView.getId(), second.getId());

        timelineService.setSeminarGroupToTimeline(timelineTestView.getId(),seminarGroup4.getId());


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
        event.setImage("https://goo.gl/D5Vsdj");
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

package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.SeminarGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Martin Kocisky, 421131
 */
@Service
@Transactional
public class TimelineFacadeImpl implements TimelineFacade {

    @Inject
    private TimelineService timelineService;

    @Inject
    private SeminarGroupService seminarGroupService;

    @Inject
    private EventService eventService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createTimeline(TimelineCreateDTO timelineDTO) {
        Timeline timeline = new Timeline();
        timeline.setName(timelineDTO.getName());
        timelineService.createTimeline(timeline);

        for (Long e : timelineDTO.getEvents()) {
            addEvent(timeline.getId(), e);
        }

        setSeminarGroup(timeline.getId(), timelineDTO.getSeminarGroup());

        return timeline.getId();
    }

    @Override
    public void addComment(Long timelineId, String comment) {
        timelineService.addComment(timelineId, comment);
    }

    @Override
    public void setSeminarGroup(Long timelineId, Long seminarGroupId) {
        timelineService.setSeminarGroupToTimeline(timelineId, seminarGroupId);
    }

    @Override
    public void removeSeminarGroup(Long timelineId) {
        timelineService.removeSeminarGroupFromTimeline(timelineId);
    }

    @Override
    public void addEvent(Long timelineId, Long eventId) {
        timelineService.addEventToTimeline(timelineId, eventId);
    }

    @Override
    public void removeEvent(Long timelineId, Long eventId) {
        timelineService.removeEventFromTimeline(timelineId, eventId);
    }

    @Override
    public TimelineDTO getTimelineById(Long timelineId) {
        Timeline timeline = timelineService.getTimelineById(timelineId);
        return beanMappingService.mapTo(timeline,TimelineDTO.class);
    }

    @Override
    public List<TimelineDTO> getAllTimelines() {
        List<Timeline> timelineList = timelineService.getAllTimelines();
//        return map(timelineList);
        return beanMappingService.mapTo(timelineList,TimelineDTO.class);
    }

    @Override
    public void deleteTimeline(Long timelineId) {
        timelineService.deleteTimeline(timelineId);
    }
//
//    private List<TimelineDTO> map(List<Timeline> timelineList) {
//        List<TimelineDTO> timelineDTOList = new ArrayList<>();
//        for (Timeline timeline : timelineList) {
//            timelineDTOList.add(innerMap(timeline));
//        }
//        return timelineDTOList;
//    }
//
//    private TimelineDTO innerMap(Timeline timeline) {
//        TimelineDTO timelineDTO = new TimelineDTO();
//        timelineDTO.setId(timeline.getId());
//        timelineDTO.setName(timeline.getName());
//        timelineDTO.setComments(timeline.getComments());
//        timelineDTO.setEvents(beanMappingService.mapTo(timeline.getEvents(), EventDTO.class));
//        timelineDTO.setSeminarGroup(beanMappingService.mapTo(timeline.getSeminarGroup(), SeminarGroupDTO.class));
//        return timelineDTO;
//    }

}

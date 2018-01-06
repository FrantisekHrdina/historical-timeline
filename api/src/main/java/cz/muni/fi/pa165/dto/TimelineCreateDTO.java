package cz.muni.fi.pa165.dto;

import java.util.*;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineCreateDTO {

    private String name;
//    private List<Long> events = new ArrayList<Long>();
//    private Long seminarGroup;

    public TimelineCreateDTO(String name) {
        this.name = name;
    }

//    public TimelineCreateDTO(String name, List<Long> events, Long seminarGroup) {
//        this.name = name;
//        this.events = events;
//        this.seminarGroup = seminarGroup;
//    }

    public TimelineCreateDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Long> getEvents() {
//        return events;
//    }
//
//    public void setEvents(List<Long> events) {
//        this.events = events;
//    }
//
//    public Long getSeminarGroup() {
//        return seminarGroup;
//    }
//
//    public void setSeminarGroup(Long seminarGroup) {
//        this.seminarGroup = seminarGroup;
//    }
}

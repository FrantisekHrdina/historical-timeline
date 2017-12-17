package cz.muni.fi.pa165.dto;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineCreateDTO {

    private String name;
    private Set<Long> events = new HashSet<>();
    private Long seminarGroup;

    public TimelineCreateDTO(String name, Set<Long> events, Long seminarGroup) {
        this.name = name;
        this.events = events;
        this.seminarGroup = seminarGroup;
    }

    public TimelineCreateDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getEvents() {
        return events;
    }

    public void setEvents(Set<Long> events) {
        this.events = events;
    }

    public Long getSeminarGroup() {
        return seminarGroup;
    }

    public void setSeminarGroup(Long seminarGroup) {
        this.seminarGroup = seminarGroup;
    }
}

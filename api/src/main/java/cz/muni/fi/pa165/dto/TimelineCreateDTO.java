package cz.muni.fi.pa165.dto;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineCreateDTO {

    private Long id;
    private String name;
    private Set<Long> events = new HashSet<>();
    private Long seminarGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

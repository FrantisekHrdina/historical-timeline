package cz.muni.fi.pa165.dto;

import org.apache.commons.beanutils.BeanComparator;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineDTO {

    private Long id;
    private String name;
    private Set<String> comments = new LinkedHashSet<>();

    private List<EventDTO> events = new ArrayList<EventDTO>();

    private SeminarGroupDTO seminarGroup;

    public TimelineDTO() {

    }

    public TimelineDTO(String name, Set<String> comments, List<EventDTO> events, SeminarGroupDTO seminarGroup) {
        this.name = name;
        this.comments = comments;
        this.events = events;
        this.seminarGroup = seminarGroup;
    }

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

    public Set<String> getComments() {
        return comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof TimelineDTO)) return false;

        TimelineDTO other = (TimelineDTO) obj;
        return Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        if (name != null){
            return 31 * name.hashCode();
        }
        return 0;
    }

    public List<EventDTO> getEvents() {
        BeanComparator fieldComparator = new BeanComparator(
                "date");
        Collections.sort(events, fieldComparator);

        return events;


    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public SeminarGroupDTO getSeminarGroup() {
        return seminarGroup;
    }

    public void setSeminarGroup(SeminarGroupDTO seminarGroup) {
        this.seminarGroup = seminarGroup;
    }


}



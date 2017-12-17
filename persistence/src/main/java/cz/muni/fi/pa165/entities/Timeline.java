package cz.muni.fi.pa165.entities;

import javax.validation.constraints.NotNull;
import java.util.*;
import javax.persistence.*;

/**
 * @author František Hrdina
 */

@Entity
public class Timeline {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false, unique=true)
    private String name;

    @OneToMany(mappedBy = "timeline")
    private Set<Event> events = new HashSet<Event>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> comments = new LinkedHashSet<>();

    @OneToOne()
    private SeminarGroup seminarGroup;

    public Timeline(){

    }

    public Timeline(String name, Set<Event> events, Set<String> comments, SeminarGroup seminarGroup) {
        this.name = name;
        this.events = events;
        this.comments = comments;
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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<String> getComments() {
        return comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    public SeminarGroup getSeminarGroup() {
        return seminarGroup;
    }

    public void setSeminarGroup(SeminarGroup seminarGroup) {
        this.seminarGroup = seminarGroup;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Timeline)) return false;

        Timeline other = (Timeline) obj;

        return Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        if (name != null){
            return 31 * name.hashCode();
        }
        else {
            return 0;
        }
    }
}
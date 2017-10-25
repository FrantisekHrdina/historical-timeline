package cz.muni.fi.pa165.entities;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    @ManyToMany
    private List<Event> events;

    private List<String> comments;

    @OneToOne
    private SeminarGroup seminarGroup;

    public Timeline(){

    }

    public Timeline(Long id, String name) {
        this.id = id;
        this.name = name;
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


    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public void AddEvent(Event event) {
        this.events.add(event);
        event.addTimeline(this);
    }

    public List<String> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(String comment) {
        this.comments.add(comment);
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
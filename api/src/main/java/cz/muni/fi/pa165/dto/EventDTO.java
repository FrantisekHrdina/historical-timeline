package cz.muni.fi.pa165.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author František Hrdina
 */
public class EventDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String location;
    private String description;
    private byte[] image;
    private List<TimelineDTO> timelines;

    public EventDTO() {
    }

    public EventDTO(Long id, String name, LocalDate date, String location, String description, byte[] image) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.image = image;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<TimelineDTO> getTimelines() {
        return Collections.unmodifiableList(timelines);
    }

    public void setTimelines(List<TimelineDTO> timelines) {
        this.timelines = timelines;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof EventDTO)) return false;

        EventDTO other = (EventDTO) obj;

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
package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Martin Kocisky, 421131
 */
public class TimelineDTO {

    private Long id;
    private String name;
    private List<String> comments = new ArrayList<String>();

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

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
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
}

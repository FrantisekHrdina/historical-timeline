package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Martin Wörgötter
 */
public class SeminarGroupCreateDTO {
	private Long id;

	@NotNull
	@Size(max = 32)
	private String name;

	private List<TimelineDTO> timelines = new ArrayList<>();

	public SeminarGroupCreateDTO(String name, List<TimelineDTO> timelines) {
		this.name = name;
		this.timelines = timelines;
	}

	public SeminarGroupCreateDTO() {
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

	public List<TimelineDTO> getTimelines() {
		return timelines;
	}

	public void setTimelines(List<TimelineDTO> timelines) {
		this.timelines = timelines;
	}

	@Override
	public String toString() {
		return "SeminarGroupCreateDTO [id=" + id + ", name=" + name
				+ ", timelines=" + timelines + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((timelines == null) ? 0 : timelines.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeminarGroupDTO other = (SeminarGroupDTO) obj;
		if (!Objects.equals(name, other.getName()))
			return false;
		if (!Objects.equals(timelines, other.getTimelines()))
			return false;
		return true;
	}
}

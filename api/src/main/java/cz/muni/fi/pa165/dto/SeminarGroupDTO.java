package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Martin Wörgötter
 */
public class SeminarGroupDTO {
	private Long id;
	private String name;
	private List<TimelineDTO> timelines = new ArrayList<>();

	public SeminarGroupDTO(Long id, String name, List<TimelineDTO> timelines) {
		this.id = id;
		this.name = name;
		this.timelines = timelines;
	}

	public SeminarGroupDTO() {
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

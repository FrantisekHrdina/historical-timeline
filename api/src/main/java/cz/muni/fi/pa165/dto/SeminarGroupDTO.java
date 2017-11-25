package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.List;

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

}

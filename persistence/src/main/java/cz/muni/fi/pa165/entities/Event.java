package cz.muni.fi.pa165.entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Martin Wörgötter
 */
public class Event {
	private Long id;
	private String name;
	private LocalDate date;
	private String location;
	private String description;
	private byte[] image;

	public Event(Long id, String name, LocalDate date, String location, String description, byte[] image) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Event other = (Event) obj;
		if (!Objects.equals(date, other.getDate()))
			return false;
		if (!Objects.equals(description, other.getDescription()))
			return false;
		if (!Objects.equals(id, other.getId()))
			return false;
		if (!Arrays.equals(image, other.getImage()))
			return false;
		if (!Objects.equals(location, other.getLocation()))
			return false;
		if (!Objects.equals(name, other.getName()))
			return false;
		return true;
	}
}
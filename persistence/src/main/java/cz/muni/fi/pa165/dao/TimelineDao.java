package cz.muni.fi.pa165.dao;

import java.util.List;

import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;

/**
 * 
 * @author Martin Wörgötter
 *
 */
public interface TimelineDao {
	/**
	 * Adds Timeline
	 * 
	 * @param timeline Timeline to be stored
	 */
	void addTimeline(Timeline timeline);

	/**
	 * Removes Timeline
	 * 
	 * @param timeline Timeline to be removed
	 */
	void removeTimeline(Timeline timeline);

	/**
	 * Edits Timeline
	 * 
	 * @param timeline Timeline to be edited
	 */
	void editTimeline(Timeline timeline);

	/**
	 * Finds Timeline by id
	 * 
	 * @param id of the desired Timeline
	 * @return Timeline
	 */
	Timeline findTimeline(Long id);

	/**
	 * Finds all Timelines
	 * 
	 * @return all Timelines
	 */
	List<Timeline> findAllTimelines();
}

package cz.muni.fi.pa165.service;

import java.util.List;
import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.entities.SeminarGroup;

@Service
public interface SeminarGroupService {

	/**
	 * Add group g to the database or update g if it already exists.
	 * 
	 * @param g
	 */
	public void saveGroup(SeminarGroup g);

	/**
	 * Delete group g from the database.
	 * 
	 * @param g
	 */
	public void removeGroup(SeminarGroup g);

	/**
	 * Find group by id and return an object.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public SeminarGroup findGroup(Long id);

	/**
	 * Find all groups and return them.
	 * 
	 * @return
	 */
	public List<SeminarGroup> findAllGroups();

}

package cz.muni.fi.pa165.facade;

import java.util.List;

import cz.muni.fi.pa165.dto.SeminarGroupCreateDTO;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;

/**
 * @author Martin Wörgötter
 */
public interface SeminarGroupFacade {
	/**
	 * Add group g to the database or update g if it already exists.
	 * 
	 * @param seminarGroupCreateDTO
	 */
	public Long saveGroup(SeminarGroupCreateDTO seminarGroupCreateDTO);

	/**
	 * Delete group g from the database.
	 * 
	 * @param id
	 */
	public void removeGroup(Long id);

	/**
	 * Find group by id and return an object.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public SeminarGroupDTO findGroup(Long id);

	/**
	 * Find all groups and return them.
	 * 
	 * @return
	 */
	public List<SeminarGroupDTO> findAllGroups();
}
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.SeminarGroup;

import java.util.List;

/**
 * This is an interface for the SeminarGroup Data Objects
 * @author Martin Kocisky
 */

public interface SeminarGroupDao {

    /**
     * Add group g to the database.
     * @param g
     */
    public void addGroup(SeminarGroup g);

    /**
     * Delete group g from the database.
     * @param g
     */
    public void removeGroup(SeminarGroup g);

    /**
     * Update group g.
     * @param g
     */
    public void editGroup(SeminarGroup g);

    /**
     * Find group by id and return an object.
     * @param id of the group
     * @return
     */
    public SeminarGroup findGroup(Long id);

    /**
     * Find all groups and return them.
     * @return
     */
    public List<SeminarGroup> findAllGroups();

}

package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Group;
import java.util.List;

/**
 * This is an interface for the SeminarGroup Data Objects
 * @author Martin Kocisky
 */

public interface SeminarGroupDao {

    /**
     * Add group g to the databse.
     * @param g
     */
    public void addGroup(Group g);

    /**
     * Delete group g from the database.
     * @param g
     */
    public void removeGroup(Group g);

    /**
     * Update group g.
     * @param g
     */
    public void editGroup(Group g);

    /**
     * Find group by id and return an object.
     * @param id of the group
     * @return
     */
    public Group findGroup(Long id);

    /**
     * Find all groups and return them.
     * @return
     */
    public List<Group> findAllGroups();

}

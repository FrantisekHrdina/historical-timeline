package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Group;
import java.util.List;

/**
 * This is an interface for the Group Data Objects
 * @author Martin Kocisky
 */

public interface GroupDao {

    /**
     * Add group g to the databse.
     * @param g
     */
    public void create(Group g);

    /**
     * Delete group g from the database.
     * @param g
     */
    public void delete(Group g);

    /**
     * Update group g.
     * @param g
     */
    public void update(Group g);

    /**
     * Find group by id and return an object.
     * @param id of the group
     * @return
     */
    public Group findById(Long id);

    /**
     * Find group by name and return an object.
     * @param name of the group
     * @return
     */
    public Group findByName(String name);

    /**
     * Find all groups.
     * @return
     */
    public List<Group> findAll();

}

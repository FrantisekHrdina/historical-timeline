package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Group;
import java.util.List;

/**
 * @author Martin Kocisky
 */

public interface GroupDao {

    public void create(Group g);

    public void delete(Group g);

    public Group findById(Long id);

    public Group findByName(String name);

    public List<Group> findAll();

}

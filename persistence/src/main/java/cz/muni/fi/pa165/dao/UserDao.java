package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.User;
import java.util.List;

/**
 * @author František Hrdina
 */

public interface UserDao {

    /**
     * Stores user in the system.
     *
     * @param user User to be stored
     */
    public void addUser(User user);

    /**
     * Removes user from the system.
     *
     * @param user User to remove
     */
    public void removeUser(User user);

    /**
     * Edits user in the system.
     *
     * @param user User to be edited
     */
    public void editUser(User user);

    /**
     * Finds user in the system by given id.
     *
     * @param id Id of the user to find
     * @return Founded user
     */
    public User findUser(Long id);

    /**
     * Finds all users that are stored in the system and their role is Student.
     *
     * @return List of founded students.
     */
    public List<User> findAllStudents();

    /**
     * Finds all users that are stored in the system and their role is Teacher.
     *
     * @return List of founded teachers.
     */
    public List<User> findAllTeachers();

}

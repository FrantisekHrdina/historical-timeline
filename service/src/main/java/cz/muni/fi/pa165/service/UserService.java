package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tibor Bujdoš
 */
@Service
public interface UserService {
    
    /**
     * Add user to database
     * 
     * @param user 
     */
    void addUser(User user);
    
    /**
     * Remove user from database
     * 
     * @param user 
     */
    void removeUser(User user);
    
    /**
     * Edit user in database
     * 
     * @param user 
     */
    void editUser(User user);
    
    /**
     * Find user by id
     * 
     * @param id
     * @return desired User
     */
    User findUser(Long id);
    
    /**
     * Find all students in database
     * 
     * @return List of students
     */
    List<User> findAllStudents(); 
    
    /**
     * Find all teachers in database
     * 
     * @return List of teachers
     */
    List<User> findAllTeachers();
}

package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserDTO;
import java.util.List;

/**
 *
 * @author Tibor Bujdoš
 */
public interface UserFacade {
    /**
     * Add user to database
     * 
     * @param user 
     */
    Long addUser(UserDTO user);
    
    /**
     * Remove user from database
     * 
     * @param user 
     */
    void removeUser(Long id);
    
    /**
     * Edit user in database
     * 
     * @param user 
     */
    void editUser(UserDTO user);
    
    /**
     * Find user by id
     * 
     * @param id
     * @return desired User
     */
    UserDTO findUser(Long id);
    
    /**
     * Find all students in database
     * 
     * @return List of students
     */
    List<UserDTO> findAllStudents(); 
    
    /**
     * Find all teachers in database
     * 
     * @return List of teachers
     */
    List<UserDTO> findAllTeachers();
}

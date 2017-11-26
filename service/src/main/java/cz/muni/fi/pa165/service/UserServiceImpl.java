package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Tibor Bujdoš
 */
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void removeUser(User user) {
        userDao.removeUser(user);
    }

    @Override
    public void editUser(User user) {
        userDao.editUser(user);
    }

    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }

    @Override
    public List<User> findAllStudents() {
        return userDao.findAllStudents();
    }

    @Override
    public List<User> findAllTeachers() {
        return userDao.findAllTeachers();
    }
    
}

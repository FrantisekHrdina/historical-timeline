package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.exception.DAOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tibor Bujdoš
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        if (user == null){
            throw new DAOException("User must not be null");
        }
        try{
            userDao.addUser(user);
        } catch (Exception e) {
            throw new DAOException("Persistance error occured", e);
        }
    }

    @Override
    public void removeUser(User user) {
        if (user == null){
            throw new DAOException("User must not be null");
        }
        try{
            userDao.removeUser(user);
        } catch (Exception e) {
            throw new DAOException("Persistance error occured", e);
        }
    }

    @Override
    public void editUser(User user) {
        if (user == null){
            throw new DAOException("User must not be null");
        }
        try{
            userDao.editUser(user);
        } catch (Exception e) {
            throw new DAOException("Persistance error occured", e);
        }
    }

    @Override
    public User findUser(Long id) {
        try {
            return userDao.findUser(id);
        } catch (Exception e) {
            throw new DAOException("Persistance error occured", e);
        }
    }

    @Override
    public List<User> findAllStudents() {
        try {
            return userDao.findAllStudents();
        } catch (Exception e) {
            throw new DAOException("Persistance error occured", e);
        }
    }

    @Override
    public List<User> findAllTeachers() {
        try {
            return userDao.findAllTeachers();
        } catch (Exception e) {
            throw new DAOException("Persistance error occured", e);
        }
    }
    
}

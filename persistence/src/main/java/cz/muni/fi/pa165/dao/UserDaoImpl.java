package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager em;

    /**
     * Stores user in the system.
     *
     * @param user User to be stored
     */
    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    /**
     * Removes user from the system.
     *
     * @param user User to remove
     */
    @Override
    public void removeUser(User user) {
        em.remove(user);
    }

    /**
     * Edits user in the system.
     *
     * @param user User to be edited
     */
    @Override
    public void editUser(User user) {
        em.merge(user);
    }

    /**
     * Finds user in the system by given id.
     *
     * @param id Id of the user to find
     * @return Founded user
     */
    @Override
    public User findUser(Long id) {
        try {
            return em.createQuery("SELECT u from User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds all users that are stored in the system and their role is Student.
     *
     * @return List of founded students.
     */
    @Override
    public List<User> findAllStudents() {
        return em.createQuery("Select u from User u WHERE u.isTeacher = false", User.class).getResultList();
    }

    /**
     * Finds all users that are stored in the system and their role is Teacher.
     *
     * @return List of founded teachers.
     */
    @Override
    public List<User> findAllTeachers() {
        return em.createQuery("Select u from User u WHERE u.isTeacher = true", User.class).getResultList();
    }
}

package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 *
 * @author Tibor Bujdoš
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests{
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private SeminarGroupDao seminarGroupDao;
    
    private User user1;
    private User user2;
    private User user3;
    private SeminarGroup seminarGroup1;
    private SeminarGroup seminarGroup2;
    
    @BeforeMethod
    public void setUp() {
        user1 = new User("John", 
                "Snow", 
                "JohnSnow@winterfell.com", 
                "youknownothingjohnsnow", 
                false);
        
        user2 = new User("khaleesi",
                "Dragonborn",
                "Khaleesi@westeros.com",
                "dracarys",
                false);
        
        user3 = new User("Cersei",
                "Lannister",
                "CerseiLannister@kingslanding.com",
                "shameshameshame",
                true);
        
        seminarGroup1 = new SeminarGroup("History of Seven Kingdoms 01");
        
        seminarGroup2 = new SeminarGroup("History of Seven Kingdoms 02");
        
        user1.addSeminarGroup(seminarGroup1);
        user2.addSeminarGroup(seminarGroup1);
        user2.addSeminarGroup(seminarGroup2);
        
        userDao.addUser(user2);
        userDao.addUser(user3);
        seminarGroupDao.addGroup(seminarGroup1);
        seminarGroupDao.addGroup(seminarGroup2);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addNullUser() {
        userDao.addUser(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void editNullUser() {
        userDao.editUser(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullUser() {
        userDao.removeUser(null);
    }
    
    @Test
    public void addUser() {
        assertThat(user1.getId()).isNull();
        user1.addSeminarGroup(seminarGroup1);
        userDao.addUser(user1);
        assertThat(user1.getId()).isNotNull();
        assertThat(userDao.findUser(user1.getId())).isEqualTo(user1);
        assertThat(userDao.findUser(user1.getId()).getSeminarGroups()).contains(seminarGroup1);
    }
    
    @Test
    public void addSameUserTwice() {
        userDao.addUser(user1);
        userDao.addUser(user1);
        assertThat(userDao.findAllStudents()).usingFieldByFieldElementComparator().contains(user1);
        //user2 is already stored and is student that is why 2
        assertThat(userDao.findAllStudents().size()).isEqualTo(2);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void addUserWithNullForename() {
        user1.setForename(null);
        userDao.addUser(user1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void addUserWithNullSurname() {
        user1.setSurname(null);
        userDao.addUser(user1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void addUserWithNullEmail() {
        user1.setEmail(null);
        userDao.addUser(user1);
    }
    
    @Test
    public void removeUser() {
        userDao.removeUser(user2);
        assertThat(userDao.findAllStudents()).isEmpty();
        assertThat(userDao.findAllTeachers()).usingFieldByFieldElementComparator().containsOnly(user3);
    }
    
    @Test
    public void editUser() {
        userDao.addUser(user1);
        user1.setSurname("Targaryen");
        userDao.editUser(user1);
        assertThat(userDao.findUser(user1.getId())).isEqualTo(user1);
    }
    
    @Test
    public void editUserGroup() {
        userDao.addUser(user1);
        user1.addSeminarGroup(seminarGroup1);
        userDao.editUser(user1);
        User user = userDao.findUser(user1.getId());
        assertThat(user.getSeminarGroups()).contains(seminarGroup1);
    }
    
    @Test
    public void findAllStudents() {
        assertThat(userDao.findAllStudents())
                .usingFieldByFieldElementComparator()
                .containsOnly(user2);
    }
    @Test
    public void findAllTeachers() {
        assertThat(userDao.findAllTeachers())
                .usingFieldByFieldElementComparator()
                .containsOnly(user3);
    }
}

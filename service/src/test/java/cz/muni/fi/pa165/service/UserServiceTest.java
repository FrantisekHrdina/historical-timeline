package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.exception.DAOException;
import cz.muni.fi.pa165.service.UserServiceImpl;
import javax.validation.ConstraintViolationException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Tibor Bujdoš
 */
//@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests{
    
    @Mock
    private UserDao userDao;
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private SeminarGroupServiceImpl seminarGroupService;
    
    private User user1;
    private User user2;
    private User user3;
    private SeminarGroup group;
    
    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);	
    }
    
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
        
        SeminarGroup group = new SeminarGroup("abc");
        seminarGroupService.saveGroup(group);
                
        userService.addUser(user2);
        userService.addUser(user3);
    }
    
    @Test(expectedExceptions = DAOException.class)
    public void addNullUser() {
        userService.addUser(null);
    }
    
    @Test(expectedExceptions = DAOException.class)
    public void editNullUser() {
        userService.editUser(null);
    }
    
    @Test(expectedExceptions = DAOException.class)
    public void removeNullUser() {
        userService.removeUser(null);
    }
    
    @Test
    public void addUser() {
        assertThat(user1.getId()).isNull();
        user1.addSeminarGroup(group);
        userService.addUser(user1);
        assertThat(user1.getId()).isNotNull();
        assertThat(userService.findUser(user1.getId())).isEqualTo(user1);
    }
    
    @Test
    public void addSameUserTwice() {
        userService.addUser(user1);
        userService.addUser(user1);
        assertThat(userService.findAllStudents()).usingFieldByFieldElementComparator().contains(user1);
        //user2 is already stored and is student that is why 2
        assertThat(userService.findAllStudents().size()).isEqualTo(2);
    }
    
    @Test(expectedExceptions = DAOException.class)
    public void addUserWithNullForename() {
        user1.setForename(null);
        userService.addUser(user1);
    }
    
    @Test(expectedExceptions = DAOException.class)
    public void addUserWithNullSurname() {
        user1.setSurname(null);
        userService.addUser(user1);
    }
    
    @Test(expectedExceptions = DAOException.class)
    public void addUserWithNullEmail() {
        user1.setEmail(null);
        userService.addUser(user1);
    }
    
    @Test
    public void removeUser() {
        userService.removeUser(user2);
        assertThat(userService.findAllStudents()).isEmpty();
        assertThat(userService.findAllTeachers()).usingFieldByFieldElementComparator().containsOnly(user3);
    }
    
    @Test
    public void editUser() {
        userService.addUser(user1);
        user1.setSurname("Targaryen");
        userService.editUser(user1);
        assertThat(userService.findUser(user1.getId())).hasFieldOrPropertyWithValue("surname", "Targaryen");
    }
    
    @Test
    public void editUserGroup() {
        userService.addUser(user1);
        user1.addSeminarGroup(group);
        userService.editUser(user1);
        User user = userService.findUser(user1.getId());
        assertThat(user.getSeminarGroups()).contains(group);
    }
    
    @Test
    public void findAllStudents() {
        assertThat(userService.findAllStudents())
                .usingFieldByFieldElementComparator()
                .containsOnly(user2);
    }
    
    @Test
    public void findAllTeachers() {
        assertThat(userService.findAllTeachers())
                .usingFieldByFieldElementComparator()
                .containsOnly(user3);
    }
    
    @Test
    public void findAllStudentsWithGroup() {
        user1.addSeminarGroup(group);
        assertThat(userService.findAllStudents())
                .usingFieldByFieldElementComparator()
                .containsOnly(user2);
    }
    
}
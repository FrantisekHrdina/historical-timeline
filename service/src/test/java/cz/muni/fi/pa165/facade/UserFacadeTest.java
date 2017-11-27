package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.exception.DAOException;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Tibor Bujdoš
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {
    
    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserFacade userFacade;
    
    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;
    
    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);	
    }
    
    @BeforeMethod
    public void setUp() {
        user1 = new UserDTO();
        user1.setForename("John"); 
        user1.setSurname("Snow");
        user1.setEmail("JohnSnow@winterfell.com"); 
        user1.setPasswordHash("youknownothingjohnsnow"); 
        user1.setIsTeacher(false);
        
        user2 = new UserDTO();
        user2.setForename("khaleesi"); 
        user2.setSurname("Dragonborn");
        user2.setEmail("Khaleesi@westeros.com"); 
        user2.setPasswordHash("dracarys"); 
        user2.setIsTeacher(false);
        
        user3 = new UserDTO();
        user3.setForename("Cersei"); 
        user3.setSurname("Lannister");
        user3.setEmail("CerseiLannister@kingslanding.com"); 
        user3.setPasswordHash("shameshameshame"); 
        user3.setIsTeacher(true);
        
        user2.setId(userFacade.addUser(user2));
        user3.setId(userFacade.addUser(user3));
    }
    
     
    
    @Test
    public void addUser() {
        assertThat(user1.getId()).isNull();
        userFacade.addUser(user1);
        assertThat(userFacade.findAllStudents()).contains(user1);
    }
    
    @Test
    public void removeUser() {
        userFacade.removeUser(user2.getId());
        assertThat(userFacade.findAllStudents()).isEmpty();
        assertThat(userFacade.findAllTeachers()).contains(user3);
    }
    
    @Test
    public void editUser() {
        user2.setSurname("Targaryen");
        userFacade.editUser(user2);
        UserDTO user = userFacade.findUser(user2.getId());
        assertThat(user.getSurname()).isEqualTo( "Targaryen");
    }
    
    @Test
    public void findAllStudents() {
        assertThat(userFacade.findAllStudents()).contains(user2);
    }
    @Test
    public void findAllTeachers() {
        assertThat(userFacade.findAllTeachers()).contains(user3);
    }
    
}

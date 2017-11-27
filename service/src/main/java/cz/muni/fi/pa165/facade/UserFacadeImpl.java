package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.UserService;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tibor Bujdoš
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade{
    
    final static Logger log = LoggerFactory.getLogger(EventFacadeImpl.class);

    @Inject
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long addUser(UserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        userService.addUser(mappedUser);
        return mappedUser.getId();
    }

    @Override
    public void removeUser(Long id) {
        userService.removeUser(userService.findUser(id));
    }

    @Override
    public void editUser(UserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        userService.editUser(mappedUser);
    }

    @Override
    public UserDTO findUser(Long id) {
        User user = userService.findUser(id);
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllStudents() {
        List<User> users = userService.findAllStudents();
        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllTeachers() {
        List<User> users = userService.findAllTeachers();
        return beanMappingService.mapTo(users, UserDTO.class);
    }
    
}

package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.SeminarGroupService;
import cz.muni.fi.pa165.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    @Inject
    private SeminarGroupService seminarGroupService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long addUser(UserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        for(SeminarGroupDTO group : user.getSeminarGroupSet()) {
            mappedUser.addSeminarGroup(seminarGroupService.findGroup(group.getId()));
        }
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
        for(SeminarGroupDTO group : user.getSeminarGroupSet()) {
            mappedUser.addSeminarGroup(seminarGroupService.findGroup(group.getId()));
        }
        userService.editUser(mappedUser);
    }

    @Override
    public UserDTO findUser(Long id) {
        User user = userService.findUser(id);
        UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
        Set<SeminarGroupDTO> groups = new HashSet<>();
        for(SeminarGroup group : user.getSeminarGroups()) {
            groups.add(beanMappingService.mapTo(group, SeminarGroupDTO.class));
        }
        userDTO.setSeminarGroupSet(groups);
        return userDTO;
    }

    @Override
    public List<UserDTO> findAllStudents() {
        List<User> users = userService.findAllStudents();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
            Set<SeminarGroupDTO> groups = new HashSet<>();
            groups.addAll(beanMappingService.mapTo(user.getSeminarGroups(), SeminarGroupDTO.class));
            userDTO.setSeminarGroupSet(groups);
//            userDTO.setId(user.getId());
//            userDTO.setForename(user.getForename());
//            userDTO.setSurname(user.getSurname());
//            userDTO.setEmail(user.getEmail());
//            userDTO.setIsTeacher(false);
//            userDTO.setPasswordHash(user.getPasswordHash());
//            Set<SeminarGroupDTO> groups = new HashSet<>();
//            for(SeminarGroup group : user.getSeminarGroups()) {
//                groups.add(beanMappingService.mapTo(group, SeminarGroupDTO.class));
//            }
//            userDTO.setSeminarGroupSet(groups);
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    @Override
    public List<UserDTO> findAllTeachers() {
        List<User> users = userService.findAllTeachers();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
            Set<SeminarGroupDTO> groups = new HashSet<>();
            groups.addAll(beanMappingService.mapTo(user.getSeminarGroups(), SeminarGroupDTO.class));
            userDTO.setSeminarGroupSet(groups);
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return beanMappingService.mapTo(userService.findUserByEmail(email), UserDTO.class);
    }
}
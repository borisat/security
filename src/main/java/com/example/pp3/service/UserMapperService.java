package com.example.pp3.service;

import com.example.pp3.dao.RoleDAO;
import com.example.pp3.dao.UserDAO;
import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.Role;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMapperService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserDTO userDTO;

    @Autowired
    private RoleDAO roleDAO;


    public UserDTO mapUserToDTO(User user) {

        userDTO.setAge(user.getAge());
        userDTO.setRoles(user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        ;
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());

        return userDTO;
    }

    public User mapDTOToUser(UserDTO userDTO) throws ControllerException {

        User user = new User();

        if (userDTO.getId() != 0) {
            user = userDAO.findById(userDTO.getId()).get();
        }

        if (userDTO.getRoles() == null || userDTO.getRoles().size() == 0) {
            userDTO.setRoles(new ArrayList<>(Collections.singleton("ROLE_USER")));
        }

        List<String> rolesDTO = userDTO.getRoles();
        List<Role> roles1 = roleDAO.findAll();
        Set<String> rolesToKeep = new HashSet<>(rolesDTO);
        roles1.removeIf(role -> !rolesToKeep.contains(role.getName()));
        Set<Role> roleSetFromDTO = new HashSet<>(roles1);

        user.setRoles(roleSetFromDTO);
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());


        return user;
    }


}

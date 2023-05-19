package com.example.pp3.Service;

import com.example.pp3.DAO.RoleDAO;
import com.example.pp3.DAO.UserDAO;
import com.example.pp3.DTO.UserDTO;
import com.example.pp3.Exception.ControllerException;
import com.example.pp3.Model.Role;
import com.example.pp3.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMapper {

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

        if (userDTO.getRoles() == null) {
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

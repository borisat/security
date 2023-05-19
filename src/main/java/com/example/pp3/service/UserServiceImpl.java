package com.example.pp3.service;

import com.example.pp3.dao.RoleDAO;
import com.example.pp3.dao.UserDAO;
import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.Role;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;


    @Autowired
    private RoleDAO roleDAO;


    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public void saveUser(UserDTO userDTO) throws ControllerException {

        User user = userMapper.mapDTOToUser(userDTO);


        if (this.emailPatternMatches(user.getEmail())) {
//            List<Role> roles1 = roleDAO.findByName("ROLE_USER");
//            user.setRoles(new HashSet<>(roles1));
            userDAO.save(user);
        } else {
            throw new ControllerException(user.getEmail());
        }
    }

    public void makeAdmin(User user) throws ControllerException {
        if (this.emailPatternMatches(user.getEmail())) {
            List<Role> roles1 = roleDAO.findByName("ROLE_ADMIN");
            Set<Role> roleSet = user.getRoles();
            roleSet.add(roles1.get(0));
            user.setRoles(roleSet);
            userDAO.save(user);
        } else {
            throw new ControllerException(user.getEmail());
        }
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public User getUserByID(int id) {
        return userDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found by ID::  " + id));
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByUsername(name);
    }

    public boolean emailPatternMatches(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }


}

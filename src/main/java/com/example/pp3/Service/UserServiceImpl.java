package com.example.pp3.Service;

import com.example.pp3.DAO.RoleDAO;
import com.example.pp3.DAO.UserDAO;
import com.example.pp3.Exception.ControllerException;
import com.example.pp3.Model.Role;
import com.example.pp3.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;


    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public void saveUser(User user) throws ControllerException {
        if (this.emailPatternMatches(user.getEmail())) {
            List<Role> roles1 = roleDAO.findByName("ROLE_USER");
            user.setRoles(new HashSet<>(roles1));
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

package com.example.pp3.service;

import com.example.pp3.dao.UserDAO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.getUserByUsername(s);
    }

    @Override
    public void saveUser(User user) throws ControllerException {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        try {
            userDAO.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ControllerException("not unique username");
        } catch (Exception e) {
            throw new ControllerException("Incorrect email");
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
}

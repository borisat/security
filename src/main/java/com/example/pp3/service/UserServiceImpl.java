package com.example.pp3.service;

import com.example.pp3.DAO.UserDAO;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public void saveUser(User user) {
        userDAO.save(user);

    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public User getUserByID(int id) {
        Optional<User> optionalUser = userDAO.findById(id);
        User user = null;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new RuntimeException("User not found by ID:: " + id);
        }
        return user;
    }
}

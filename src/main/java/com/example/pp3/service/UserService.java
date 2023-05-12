package com.example.pp3.service;

import com.example.pp3.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user);

    void deleteUser(int id);

    User getUserByID(int id);


}

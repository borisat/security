package com.example.pp3.service;

import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user) throws ControllerException;

    void deleteUser(int id);

    User getUserByID(int id);

    User getUserByName(String name);


}

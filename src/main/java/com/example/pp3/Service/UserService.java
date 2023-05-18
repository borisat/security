package com.example.pp3.Service;

import com.example.pp3.Exception.ControllerException;
import com.example.pp3.Model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user) throws ControllerException;

    void makeAdmin(User user) throws ControllerException;

    void deleteUser(int id);

    User getUserByID(int id);

    User getUserByName(String name);


}

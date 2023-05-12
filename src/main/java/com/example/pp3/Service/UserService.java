package com.example.pp3.Service;

import com.example.pp3.Model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user);

    void deleteUser(int id);

    User getUserByID(int id);


}

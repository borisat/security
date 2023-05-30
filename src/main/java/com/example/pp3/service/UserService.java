package com.example.pp3.service;

import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.EmailValidationException;
import com.example.pp3.exception.NonUniqueUsernameException;
import com.example.pp3.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user) throws NonUniqueUsernameException, EmailValidationException;

    void deleteUser(int id);

    User getUserByID(int id);

    UserDTO getUserDTOByID(int id);

    User getUserByName(String name);

    User getUserFromDTO(UserDTO userDTO) throws NonUniqueUsernameException, EmailValidationException;

    List<UserDTO> getUsersDTO();


}

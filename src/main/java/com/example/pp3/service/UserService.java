package com.example.pp3.service;

import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.EmailValidationException;
import com.example.pp3.exception.InvalidTokenException;
import com.example.pp3.exception.NonUniqueUsernameException;
import com.example.pp3.model.User;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user) throws NonUniqueUsernameException, EmailValidationException;

    void deleteUser(int id);

    User getUserByID(int id);

    UserDTO getUserDTOByID(int id);

    UserDTO getUserDTOByToken(String token) throws InvalidTokenException;

    User findUserByEmail(String email);

    User getUserFromDTO(UserDTO userDTO) throws NonUniqueUsernameException, EmailValidationException;

    List<UserDTO> getUsersDTO();

    void updatePassword(UserDTO userDTO);
}

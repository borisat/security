package com.example.pp3.service;

import com.example.pp3.dao.PasswordResetTokenDAO;
import com.example.pp3.dao.UserDAO;
import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.EmailValidationException;
import com.example.pp3.exception.NonUniqueUsernameException;
import com.example.pp3.mapper.UserMapperService;
import com.example.pp3.model.EmailDetails;
import com.example.pp3.model.PasswordResetToken;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserMapperService userMapperService;

    @Autowired
    PasswordResetTokenDAO passwordResetTokenDAO;

    @Override
    public UserDTO getUserDTOByToken(String token) {

        int userId = passwordResetTokenDAO.findByToken(token).getUser().getId();

        return getUserDTOByID(userId);
    }

    @Override
    public User findUserByEmail(String email) {

        List<User> userList = userDAO.findByEmail(email);

        return userList.get(0);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    public List<UserDTO> getUsersDTO() {

        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = this.getUsers();

        userList.forEach(user -> userDTOList.add(userMapperService.mapUserToDTO(user)));

        return userDTOList;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.getUserByUsername(s);
    }

    @Override
    public void saveUser(User user) throws NonUniqueUsernameException, EmailValidationException {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        try {
            userDAO.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new NonUniqueUsernameException(user.getUsername());
        } catch (Exception e) {
            throw new EmailValidationException(user.getEmail());
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
    public User getUserFromDTO(UserDTO userDTO) {
        return userMapperService.mapDTOToUser(userDTO);
    }

    @Override
    public UserDTO getUserDTOByID(int id) {
        return userMapperService.mapUserToDTO(getUserByID(id));
    }


    @Override
    public void updatePassword(UserDTO userDTO) {
        int id = userDTO.getId();
        User user = userDAO.getById(id);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userDAO.save(user);
    }
}




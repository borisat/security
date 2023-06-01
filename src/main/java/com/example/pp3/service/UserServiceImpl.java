package com.example.pp3.service;

import com.example.pp3.MessageProps;
import com.example.pp3.dao.RoleDAO;
import com.example.pp3.dao.UserDAO;
import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.EmailValidationException;
import com.example.pp3.exception.NonUniqueUsernameException;
import com.example.pp3.mapper.UserMapperService;
import com.example.pp3.model.EmailDetails;
import com.example.pp3.model.Role;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserMapperService userMapperService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageProps messageProps;

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
    public User getUserFromDTO(UserDTO userDTO) throws NonUniqueUsernameException, EmailValidationException {
        return userMapperService.mapDTOToUser(userDTO);
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByUsername(name);
    }

    @Override
    public UserDTO getUserDTOByID(int id) {
        return userMapperService.mapUserToDTO(getUserByID(id));
    }


    @Scheduled(fixedDelay = 86400000) //раз в сутки
    public void sendBirthdayNotification() {
        List<User> adminList = userDAO.findByRoles(roleDAO.findByName("ROLE_ADMIN").get(0));
        List<User> bDayUserList = this.getUsers();
        bDayUserList.removeIf(user -> !checkUserBday(user));

        String subject = "Поздравь";
        String messageBody = bDayUserList
                .stream()
                .map(user -> user.getUsername() + " " + user.getBirthDate() + "\n")
                .collect(Collectors.joining());


        adminList.forEach(user -> emailService.
                sendSimpleMail(new EmailDetails(user.getEmail(), messageBody, subject)));
    }

    public boolean checkUserBday(User user) {

        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());
        LocalDate userBday = LocalDate.of(LocalDate.now().getYear(),
                user.getBirthDate().getMonth(),
                user.getBirthDate().getDayOfMonth());

        Period period = Period.between(localDate, userBday);

        if (period.getDays() == messageProps.getbDayNotificationPeriod()) {
            return true;
        }
        return false;
    }


}




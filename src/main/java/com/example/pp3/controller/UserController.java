package com.example.pp3.controller;

import com.example.pp3.dao.RoleDAO;
import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.Role;
import com.example.pp3.model.User;
import com.example.pp3.service.UserMapperService;
import com.example.pp3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.util.HashSet;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserMapperService userMapperService;

    @GetMapping("/")
    public String hello() {
        return "users/index";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("age", user.getAge());
        model.addAttribute("pass", user.getPassword());
        model.addAttribute("roles", user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", ")));

        return "users/user";
    }


    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users/users";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        User user = new User(new HashSet<>(roleDAO.findAll()));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleDAO.findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        return "users/new_user";
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") UserDTO user) throws ControllerException {
        try {
            userService.saveUser(user);
        } catch (ControllerException e) {
            throw e;
        }
        return "redirect:/users";
    }

    @GetMapping("/makeAdmin/{id}")
    public String makeAdmin(@PathVariable(value = "id") int id, Model model) throws ControllerException {

        User user = userService.getUserByID(id);
        userService.makeAdmin(user);

        model.addAttribute("users", userService.getUsers());
        return "users/users";
    }


    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable(value = "id") int id, Model model) {

        UserDTO user = userMapperService.mapUserToDTO(userService.getUserByID(id));


        model.addAttribute("user", user);
        model.addAttribute("roles", roleDAO.findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        ;
        return "users/edit_user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id) {

        userService.deleteUser(id);
        return "redirect:/users";
    }


}

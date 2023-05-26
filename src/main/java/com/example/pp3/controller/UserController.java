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


    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/register";
    }
}

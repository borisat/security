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

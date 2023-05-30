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


    @GetMapping("/")
    public String hello() {
        return "users/index";
    }

    @GetMapping("/user")
    public String user() {
        return "users/user";
    }

    @GetMapping("/users")
    public String getAll() {
        return "users/users";
    }

    @GetMapping("/newUser")
    public String newUser() {
        return "users/new_user";
    }

    @GetMapping("/register")
    public String register() {
        return "users/register";
    }
}

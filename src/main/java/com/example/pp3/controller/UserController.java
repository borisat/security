package com.example.pp3.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "users/forgotPassword";
    }

}

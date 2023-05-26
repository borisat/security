package com.example.pp3.controller;

import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.User;
import com.example.pp3.service.UserMapperService;
import com.example.pp3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLOutput;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapperService userMapperService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDTO findById(@PathVariable int userId) {
        return userMapperService.mapUserToDTO(userService.getUserByID(userId));
    }

    @GetMapping("/userProfile")
    public UserDTO myProfile(@AuthenticationPrincipal User user) {
        return userMapperService.mapUserToDTO(user);
    }

    @PostMapping
    public void create(@Valid @RequestBody UserDTO userDTO) throws ControllerException {
        userService.saveUser(userMapperService.mapDTOToUser(userDTO));
    }

    @PostMapping("/delete")
    public void deleteById(@RequestBody int userId) throws ControllerException {
        userService.deleteUser(userId);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserEmail(Principal principal) {
        return userService.getUserByName(principal.getName()).getEmail();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserRoles(Principal principal) {
        return String.join(" ", userMapperService.mapUserToDTO(userService.getUserByName(principal.getName())).getRoles());
    }
}

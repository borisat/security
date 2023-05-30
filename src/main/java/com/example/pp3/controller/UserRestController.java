package com.example.pp3.controller;

import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.ControllerException;
import com.example.pp3.model.User;
import com.example.pp3.service.UserMapperService;
import com.example.pp3.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
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

    @GetMapping
    public UserDTO userProfile(@AuthenticationPrincipal User user) {
        return userMapperService.mapUserToDTO(user);
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsersDTO();
    }

    @PostMapping("/account")
    public ResponseEntity<String> put(@Valid @RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userMapperService.mapDTOToUser(userDTO));
            return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
        } catch (ControllerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDTO userDTO) throws ControllerException {
        userService.saveUser(userMapperService.mapDTOToUser(userDTO));
    }

    @DeleteMapping
    public void deleteById(@RequestBody int userId) {
        userService.deleteUser(userId);
    }

}

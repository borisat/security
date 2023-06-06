package com.example.pp3.controller;

import com.example.pp3.dto.UserDTO;
import com.example.pp3.exception.EmailValidationException;
import com.example.pp3.exception.InvalidTokenException;
import com.example.pp3.exception.NonUniqueUsernameException;
import com.example.pp3.exception.UserNotFoundException;
import com.example.pp3.model.User;
import com.example.pp3.service.TokenService;
import com.example.pp3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDTO findById(@PathVariable int userId) {
        return userService.getUserDTOByID(userId);
    }


    @GetMapping
    public UserDTO userProfile(@AuthenticationPrincipal User user) {
        return userService.getUserDTOByID(user.getId());
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsersDTO();
    }


    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDTO userDTO) throws EmailValidationException, NonUniqueUsernameException {
        userService.saveUser(userService.getUserFromDTO(userDTO));
    }

    @DeleteMapping
    public void deleteById(@RequestBody int userId) {
        userService.deleteUser(userId);
    }


    @PostMapping("/account")
    public ResponseEntity<String> post(@Valid @RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userService.getUserFromDTO(userDTO));
            return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
        } catch (NonUniqueUsernameException | EmailValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgotPassword")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody UserDTO userDTO, HttpServletRequest request) throws UserNotFoundException, UnknownHostException {
        User user = userService.findUserByEmail(userDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException(userDTO.getEmail());
        }
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        tokenService.createToken(user, appUrl);

    }

    @GetMapping("/resetPassword/{token}")
    public ModelAndView showResetPasswordPage(@PathVariable String token) throws InvalidTokenException {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = userService.getUserDTOByToken(token);
        modelAndView.addObject("user", userDTO);
        modelAndView.setViewName("/users/resetPassword"); // название HTML шаблона
        return modelAndView;
    }

    @PutMapping("/resetPassword")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.updatePassword(userDTO);
    }

}

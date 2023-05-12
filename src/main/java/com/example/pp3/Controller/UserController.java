package com.example.pp3.Controller;

import com.example.pp3.Model.User;
import com.example.pp3.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users/users";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable(value = "id") int id, Model model) {

        User user = userService.getUserByID(id);

        model.addAttribute("user", user);
        return "users/edit_user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id) {

        userService.deleteUser(id);
        return "redirect:/users";
    }


}

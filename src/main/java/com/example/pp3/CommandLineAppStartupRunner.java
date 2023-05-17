package com.example.pp3;

import com.example.pp3.DAO.RoleDAO;
import com.example.pp3.DAO.UserDAO;
import com.example.pp3.Model.Role;
import com.example.pp3.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public void run(String... args) throws Exception {
        createAdmin();
    }

    public void createAdmin() {
        if (userDAO.getUserByUsername("admin") != null) {
            System.out.println("admin exists");
        } else {
            User admin = new User("admin", "admin");
            List<Role> roles = roleDAO.findAll();
            admin.setRoles(new HashSet<>(roles));
            userDAO.save(admin);
            System.out.println("admin created");
        }
    }

}
package com.example.pp3;

import com.example.pp3.dao.RoleDAO;
import com.example.pp3.dao.UserDAO;
import com.example.pp3.model.Role;
import com.example.pp3.model.User;
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
        createRoles();
        createAdmin();
    }


    public void createRoles() {
        if (roleDAO.findAll().size() > 0) {
            System.out.println("Roles exist");
        } else {
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");

            roleDAO.save(adminRole);
            roleDAO.save(userRole);

            System.out.println("Roles created");
        }
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
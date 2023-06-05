package com.example.pp3.dao;

import com.example.pp3.model.Role;
import com.example.pp3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name = :username")
    User getUserByUsername(@Param("username") String username);

    List<User> findByRoles(Role role);

    List<User> findByEmail(String email);

}

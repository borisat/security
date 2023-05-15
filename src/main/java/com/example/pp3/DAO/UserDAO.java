package com.example.pp3.DAO;

import com.example.pp3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name = :username")
    User getUserByUsername(@Param("username") String username);
}

package com.example.pp3.DAO;

import com.example.pp3.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface UserDAO extends JpaRepository<User, Integer> {
}

package com.example.pp3.dao;

import com.example.pp3.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenDAO extends JpaRepository<PasswordResetToken, Integer> {

    public PasswordResetToken findByToken(String token);


}

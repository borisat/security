package com.example.pp3.service;

import com.example.pp3.dao.PasswordResetTokenDAO;
import com.example.pp3.model.EmailDetails;
import com.example.pp3.model.PasswordResetToken;
import com.example.pp3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class TokenService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenDAO passwordResetTokenDAO;


    public void createToken(User user, String appUrl) throws UnknownHostException {


        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        String token = passwordResetToken.getToken();

        passwordResetTokenDAO.save(passwordResetToken);
        emailService.sendSimpleMail(new EmailDetails(user.getEmail(), appUrl + "/api/users/resetPassword/" + token, "Passwrd reset link"));
    }
}

package com.example.pp3.scheduled;

import com.example.pp3.dao.PasswordResetTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenScheduler {

    @Autowired
    private PasswordResetTokenDAO passwordResetTokenDAO;

    @Scheduled(fixedDelayString = "${messages.notification-send-delay}")
    public void deleteExpiredToken() {
        passwordResetTokenDAO.findAll()
                .forEach(passwordResetToken -> {
                    if (passwordResetToken.getExpiryDateTime().isBefore(LocalDateTime.now())) {
                        passwordResetTokenDAO.delete(passwordResetToken);
                    }
                });
    }

}

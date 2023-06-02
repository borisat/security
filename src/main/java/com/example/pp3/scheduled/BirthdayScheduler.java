package com.example.pp3.scheduled;

import com.example.pp3.MessageProps;
import com.example.pp3.dao.RoleDAO;
import com.example.pp3.dao.UserDAO;
import com.example.pp3.model.EmailDetails;
import com.example.pp3.model.User;
import com.example.pp3.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayScheduler {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageProps messageProps;

    @Scheduled(fixedDelayString = "${messages.notification-send-delay}")
    public void sendBirthdayNotification() {
        List<User> adminList = userDAO.findByRoles(roleDAO.findByName("ROLE_ADMIN").get(0));
        List<User> bDayUserList = userDAO.findAll();
        bDayUserList.removeIf(user -> !checkUserBday(user));

        String subject = "Поздравь";
        String messageBody = bDayUserList
                .stream()
                .map(user -> user.getUsername() + " " + user.getBirthDate() + "\n")
                .collect(Collectors.joining());


        adminList.forEach(user -> emailService.
                sendSimpleMail(new EmailDetails(user.getEmail(), messageBody, subject)));
    }

    public boolean checkUserBday(User user) {

        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());
        LocalDate userBday = LocalDate.of(LocalDate.now().getYear(),
                user.getBirthDate().getMonth(),
                user.getBirthDate().getDayOfMonth());

        Period period = Period.between(localDate, userBday);

        if (period.getDays() == messageProps.getbDayNotificationPeriod()) {
            return true;
        }
        return false;
    }
}

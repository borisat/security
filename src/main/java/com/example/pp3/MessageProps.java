package com.example.pp3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "messages")
public class MessageProps {


    private int bDayNotificationPeriod;

    private int notificationSendDelay;


    public int getNotificationSendDelay() {
        return notificationSendDelay;
    }

    public void setNotificationSendDelay(int notificationSendDelay) {
        this.notificationSendDelay = notificationSendDelay;
    }


    public int getbDayNotificationPeriod() {
        return bDayNotificationPeriod;
    }

    public void setbDayNotificationPeriod(int bDayNotificationPeriod) {
        this.bDayNotificationPeriod = bDayNotificationPeriod;
    }
}

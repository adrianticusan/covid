package com.covid19.match.amazon.models;

import lombok.Data;

public class NotificationMessage {
    String notificationType;

    public void setNotificationType(String notificationtype) {
        this.notificationType = notificationtype;
    }
}

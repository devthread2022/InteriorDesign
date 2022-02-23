package com.jvt.interior.Model;

public class NotificationModel {
    private String notificationBody;
    private String notificationDate;
    private String notificationHeading;

    public NotificationModel() {
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationHeading() {
        return notificationHeading;
    }

    public void setNotificationHeading(String notificationHeading) {
        this.notificationHeading = notificationHeading;
    }

    public NotificationModel(String notificationBody, String notificationDate, String notificationHeading) {
        this.notificationBody = notificationBody;
        this.notificationDate = notificationDate;
        this.notificationHeading = notificationHeading;
    }
}

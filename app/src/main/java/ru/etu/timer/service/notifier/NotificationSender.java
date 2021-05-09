package ru.etu.timer.service.notifier;


public interface NotificationSender {
    void send();

    void setContentText(CharSequence message);

}

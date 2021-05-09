package ru.etu.timer.service.notifier;


import ru.etu.timer.MainActivity;

public interface NotificationSender {
    void send();

    void setContentText(CharSequence message);

    void createChannel(Object source);
}

package ru.etu.timer.service.notifier;

import ru.etu.timer.dto.TimerData;

public interface Notifier {
    void sendNotification(TimerData data);
}

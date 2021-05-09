package ru.etu.timer.service.notifier;

import androidx.core.app.NotificationCompat;

import ru.etu.timer.R;
import ru.etu.timer.dto.TimerData;

public class SimpleOnFinishNotifier implements Notifier {

    private final NotificationSender sender;

    public SimpleOnFinishNotifier(NotificationSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendNotification(TimerData data) {
        sender.setContentText(buildMessage(data));
        sender.send();
    }

    private CharSequence buildMessage(TimerData data) {
        return String.format("The timer you set on %s has ended.", data.getValue().toFormattedString());
    }
}

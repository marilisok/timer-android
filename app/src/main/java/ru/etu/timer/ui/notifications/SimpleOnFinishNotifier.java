package ru.etu.timer.ui.notifications;

import androidx.core.app.NotificationCompat;

import javax.inject.Inject;

import ru.etu.timer.R;
import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.notifier.NotificationSender;
import ru.etu.timer.service.notifier.Notifier;

public class SimpleOnFinishNotifier implements Notifier {

    @Inject
    NotificationSender sender;

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

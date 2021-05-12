package ru.etu.timer.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.logging.Logger;

import ru.etu.timer.R;
import ru.etu.timer.service.notifier.NotificationSender;

public class TimerOnFinishNotificationSender implements NotificationSender {
    private final static String CHANNEL_ID = "TimerFinished";
    private final NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.ui.notifications");

    public TimerOnFinishNotificationSender(Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("Timer has ended")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    @Override
    public void send() {
        notificationManager.notify(getUniqueNotificationId(), builder.build());
        LOGGER.info("Sent notification");
    }

    @Override
    public void setContentText(CharSequence message) {
        builder = builder.setContentText(message);
    }

    private int getUniqueNotificationId() {
        return (int) System.currentTimeMillis();
    }

    @Override
    public void createChannel(Object source) {
        Context context = (Context) source;
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager.getNotificationChannel(CHANNEL_ID) != null)
            return;

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Timer notification on end",
                NotificationManager.IMPORTANCE_HIGH
        );
        notificationManager.createNotificationChannel(channel);
        LOGGER.info(String.format("Channel %s has been created", CHANNEL_ID));
    }
}

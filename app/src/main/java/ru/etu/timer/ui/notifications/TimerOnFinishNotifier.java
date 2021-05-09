package ru.etu.timer.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.etu.timer.R;
import ru.etu.timer.service.notifier.NotificationSender;

public class TimerOnFinishNotifier implements NotificationSender {
    private final static String CHANNEL_ID = "TimerFinished";
    private final NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;

    public TimerOnFinishNotifier(Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("Timer has ended")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    @Override
    public void send() {
        notificationManager.notify(getUniqueNotificationId(), builder.build());
    }

    @Override
    public void setContentText(CharSequence message) {
        builder = builder.setContentText(message);
    }

    private int getUniqueNotificationId() {
        return (int) System.currentTimeMillis();
    }

    public static void createChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Timer notification on end",
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}

package ru.etu.timer.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.etu.timer.service.notifier.NotificationSender;
import ru.etu.timer.ui.notifications.TimerOnFinishNotificationSender;

@Module(includes = ContextModule.class)
public class NotificationSenderModule {

    @Provides
    @Singleton
    NotificationSender provideNotificationSender(Context context) {
        return new TimerOnFinishNotificationSender(context);
    }
}

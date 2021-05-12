package ru.etu.timer.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.etu.timer.service.notifier.NotificationSender;
import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.ui.notifications.SimpleOnFinishNotifier;

@Module(includes = NotificationSenderModule.class)
public class NotifierModule {

    @Provides
    @Singleton
    Notifier provideNotifier(NotificationSender sender) {
        return new SimpleOnFinishNotifier(sender);
    }
}

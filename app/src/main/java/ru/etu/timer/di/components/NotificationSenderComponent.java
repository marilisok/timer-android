package ru.etu.timer.di.components;

import javax.inject.Singleton;

import dagger.Component;

import ru.etu.timer.di.modules.NotificationSenderModule;
import ru.etu.timer.ui.notifications.SimpleOnFinishNotifier;

@Component(modules = {
        NotificationSenderModule.class
})
@Singleton
public interface NotificationSenderComponent {

    void inject(SimpleOnFinishNotifier notifier);
}

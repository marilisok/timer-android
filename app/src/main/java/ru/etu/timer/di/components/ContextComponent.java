package ru.etu.timer.di.components;


import javax.inject.Singleton;

import dagger.Component;
import ru.etu.timer.MainActivity;
import ru.etu.timer.di.modules.ContextModule;
import ru.etu.timer.di.modules.NotificationSenderModule;
import ru.etu.timer.di.modules.NotifierModule;
import ru.etu.timer.di.modules.StorageModule;

@Component(modules = {
        ContextModule.class,
        NotifierModule.class,
        StorageModule.class,
        NotificationSenderModule.class
})
@Singleton
public interface ContextComponent {
    void inject(MainActivity activity);
}

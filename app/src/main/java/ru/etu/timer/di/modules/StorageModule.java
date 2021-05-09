package ru.etu.timer.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.ui.storage.SharedPreferencesStorage;

@Module(includes = ContextModule.class)
public class StorageModule {
    @Provides
    @Singleton
    Storage provideNotificationSender(Context context) {
        return new SharedPreferencesStorage(context);
    }
}

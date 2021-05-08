package ru.etu.timer.service.timer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.utils.TimeContainer;

public class TimerFacade implements Timer {
    private final Timer timer;
    private final Notifier notifier;
    private final Storage storage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TimerFacade(TimeContainer timeScheduled, Notifier notifier, Storage storage) {
        this.timer = new StandardTimerImpl(timeScheduled.toSeconds(), this::finish);
        this.notifier = notifier;
        this.storage = storage;
    }


    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void pause() {
        timer.pause();
    }

    private void finish(TimerData data) {
        storage.save(data);
        notifier.sendNotification(data);
    }

    @Override
    public void end() {
        timer.end();
    }
}

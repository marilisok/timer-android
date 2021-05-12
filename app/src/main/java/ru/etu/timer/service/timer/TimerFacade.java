package ru.etu.timer.service.timer;


import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.utils.TimerEventListenerBuilder;

public class TimerFacade implements Timer {
    private final Timer timer;
    private final Notifier notifier;
    private final Storage storage;

    public TimerFacade(TimeContainer timeScheduled, Notifier notifier, Storage storage, TimerEventListenerBuilder eventListener) {
        eventListener.onFinish(this::finish);
        this.timer = new ThreadedTimerImpl(timeScheduled.toSeconds(), eventListener.build());
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

    @Override
    public void tcontinue() {
        timer.tcontinue();
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

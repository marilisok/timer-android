package ru.etu.timer.service.timer;


import android.annotation.SuppressLint;

import ru.etu.timer.dto.TimerData;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.utils.TimerEventListener;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ThreadedTimerImpl implements Timer {
    private final AtomicInteger secondsScheduled;
    private volatile boolean isPaused;
    private final Runner threadRunner;
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.service.timer.ThreadedTimerImpl");
    private TimerData.Builder dataBuilder;
    private final TimerEventListener eventListener;

    public ThreadedTimerImpl(int secondsScheduled, TimerEventListener eventListener) {
        this.secondsScheduled = new AtomicInteger();
        this.secondsScheduled.set(secondsScheduled);
        isPaused = true;
        threadRunner = new Runner();
        LOGGER.info("ThreadedTimerImpl has been created");
        dataBuilder = new TimerData.Builder();
        dataBuilder = dataBuilder.setId(UUID.randomUUID().toString())
                .setStartDateTime(LocalDateTime.now())
                .setValue(new TimeContainer(secondsScheduled));
        this.eventListener = eventListener;
    }

    @Override
    public void start() {
        threadRunner.start();
        isPaused = false;
        LOGGER.info("ThreadedTimerImpl has been started");
    }

    @Override
    public void pause() {
        isPaused = true;
        LOGGER.info("ThreadedTimerImpl has been paused");
    }

    @Override
    public void tcontinue() {
        LOGGER.info("ThreadedTimerImpl has been continued");
        isPaused = false;
    }

    @Override
    public void end() {
        if (!threadRunner.isCompleted)
            threadRunner.interrupt();
        LOGGER.info("ThreadedTimerImpl has been ended");
        eventListener.finish(dataBuilder.setEndDateTime(LocalDateTime.now()).build());
    }

    private class Runner extends Thread {
        public boolean isCompleted = false;

        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            try {
                while (secondsScheduled.get() > 0) {
                    while (isPaused) {}
                    eventListener.update(new TimeContainer(secondsScheduled.get()));
                    LOGGER.info(String.format("Seconds remain: %d", secondsScheduled.get()));
                    Thread.sleep(1000);
                    secondsScheduled.decrementAndGet();
                }
                eventListener.update(new TimeContainer(secondsScheduled.get()));
            } catch (InterruptedException ignored) {
                return;
            }
            isCompleted = true;
            end();
        }
    }

}

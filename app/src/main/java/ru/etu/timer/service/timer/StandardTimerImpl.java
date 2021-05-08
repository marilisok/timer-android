package ru.etu.timer.service.timer;


import ru.etu.timer.dto.TimerData;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.utils.TimerEventListener;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

public class StandardTimerImpl implements Timer {
    private final int value;
    private volatile boolean isPaused;
    private final Runner threadRunner;
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.service.timer.StandardTimerImpl");
    private TimerData.Builder dataBuilder;
    private final TimerEventListener eventListener;

    public StandardTimerImpl(int secondsScheduled, TimerEventListener eventListener) {
        value = secondsScheduled;
        isPaused = true;
        threadRunner = new Runner();
        threadRunner.start();
        LOGGER.info("StandardTimerImpl has been created");
        dataBuilder = new TimerData.Builder();
        dataBuilder = dataBuilder.setId(UUID.randomUUID().toString())
                .setStartDateTime(LocalDateTime.now())
                .setValue(new TimeContainer(secondsScheduled));
        this.eventListener = eventListener;
    }

    @Override
    public void start() {
        isPaused = false;
        LOGGER.info("StandardTimerImpl has been started");
    }

    @Override
    public void pause() {
        isPaused = true;
        LOGGER.info("StandardTimerImpl has been paused");
    }

    @Override
    public void end() {
        if (!threadRunner.isCompleted)
            threadRunner.interrupt();
        LOGGER.info("StandardTimerImpl has been ended");
        eventListener.finish(dataBuilder.setEndDateTime(LocalDateTime.now()).build());
    }

    private class Runner extends Thread {
        public boolean isCompleted = false;

        @Override
        public void run() {
            int secondsScheduled = value;
            try {
                while (secondsScheduled > 0) {
                    while (isPaused) {}
                    eventListener.update(new TimeContainer(secondsScheduled));
                    LOGGER.info("Seconds remain: " + secondsScheduled); // rewrite!!
                    Thread.sleep(1000);
                    secondsScheduled--;
                }
                eventListener.update(new TimeContainer(secondsScheduled));
            } catch (InterruptedException ignored) {
            }
            isCompleted = true;
            end();
        }
    }

}

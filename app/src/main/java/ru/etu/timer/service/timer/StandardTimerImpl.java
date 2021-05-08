package ru.etu.timer.service.timer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import ru.etu.timer.dto.TimerData;
import ru.etu.timer.utils.TimeContainer;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class StandardTimerImpl implements Timer {
    private final int value;
    private volatile boolean isPaused;
    private final Runner threadRunner;
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.service.timer.StandardTimerImpl");
    private TimerData.Builder dataBuilder;
    private final Consumer<TimerData> callback;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StandardTimerImpl(int secondsScheduled, Consumer<TimerData> callback) {
        value = secondsScheduled;
        isPaused = true;
        threadRunner = new Runner();
        threadRunner.start();
        LOGGER.info("StandardTimerImpl has been created");
        dataBuilder = new TimerData.Builder();
        dataBuilder = dataBuilder.setId(UUID.randomUUID().toString())
                .setStartDateTime(LocalDateTime.now())
                .setValue(new TimeContainer(secondsScheduled));
        this.callback = callback;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void end() {
        if (!threadRunner.isCompleted)
            threadRunner.interrupt();
        LOGGER.info("StandardTimerImpl has been ended");
        callback.accept(dataBuilder.setEndDateTime(LocalDateTime.now()).build());
    }

    private class Runner extends Thread {
        public boolean isCompleted = false;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            int secondsScheduled = value;
            try {
                while (secondsScheduled >  0) {
                    while (isPaused) {}
                    LOGGER.info("Seconds remain: " + secondsScheduled); // rewrite!!
                    Thread.sleep(1000);
                    secondsScheduled--;
                }
            } catch (InterruptedException ignored) {
            }
            isCompleted = true;
            end();
        }
    }

}

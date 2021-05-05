package ru.etu.timer.service;

import java.util.logging.Logger;

public class StandardTimerImpl implements Timer {
    private final int value;
    private volatile boolean isPaused;
    private final Runner threadRunner;
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.service.StandardTimerImpl");

    public StandardTimerImpl(int secondsScheduled) {
        this.value = secondsScheduled;
        this.isPaused = true;
        this.threadRunner = new Runner();
        this.threadRunner.start();
        LOGGER.info("StandardTimerImpl has been created");
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
    }

    private class Runner extends Thread {
        public boolean isCompleted = false;

        @Override
        public void run() {
            int secondsScheduled = value;
            try {
                while (secondsScheduled >  0) {
                    while (isPaused) {
                        Thread.onSpinWait();
                    }
                    LOGGER.info("Seconds passed: " + secondsScheduled); // rewrite!!
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

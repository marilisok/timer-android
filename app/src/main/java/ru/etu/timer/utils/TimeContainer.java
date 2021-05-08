package ru.etu.timer.utils;

public class TimeContainer {
    private final int hours;
    private final int minutes;
    private final int seconds;

    public TimeContainer(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public TimeContainer(int seconds) {
        this((seconds / 3600) % 24, (seconds / 60) % 60, seconds % 60);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int toSeconds() {
        return 3600 * hours + 60 * minutes + seconds;
    }
}

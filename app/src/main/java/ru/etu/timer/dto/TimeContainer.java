package ru.etu.timer.dto;

import android.annotation.SuppressLint;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static TimeContainer fromFormattedString(String string) {
        List<Integer> timeChunks = Arrays.stream(string.split(":"))
                .map(Integer::parseInt).collect(Collectors.toList());
        return new TimeContainer(timeChunks.get(0), timeChunks.get(1), timeChunks.get(2));
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

    @SuppressLint("DefaultLocale")
    public String toFormattedString() {
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }
}

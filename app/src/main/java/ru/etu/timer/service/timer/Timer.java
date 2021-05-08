package ru.etu.timer.service.timer;

import ru.etu.timer.dto.TimerData;

public interface Timer {
    void start();

    void pause();

    void end();
}

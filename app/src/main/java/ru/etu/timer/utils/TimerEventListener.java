package ru.etu.timer.utils;

import java.util.function.Consumer;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.dto.TimerData;

public interface TimerEventListener {
    void update(TimeContainer currentTime);

    void finish(TimerData timerData);
}

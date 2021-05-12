package ru.etu.timer.utils;

import java.util.EventListener;
import java.util.function.Consumer;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.dto.TimerData;

public interface TimerEventListenerBuilder {
    void onUpdate(Consumer<TimeContainer> currentTimeConsumer);

    void onFinish(Consumer<TimerData> timerDataConsumer);

    TimerEventListener build();
}

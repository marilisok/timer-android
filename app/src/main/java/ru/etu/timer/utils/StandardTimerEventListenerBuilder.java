package ru.etu.timer.utils;

import java.util.function.Consumer;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.dto.TimerData;

public class StandardTimerEventListenerBuilder implements TimerEventListenerBuilder {
    private Consumer<TimeContainer> updateFn;
    private Consumer<TimerData> finishFn;

    @Override
    public void onUpdate(Consumer<TimeContainer> currentTimeConsumer) {
        updateFn = currentTimeConsumer;
    }

    @Override
    public void onFinish(Consumer<TimerData> timerDataConsumer) {
        finishFn = timerDataConsumer;
    }

    @Override
    public TimerEventListener build() {
        return new StandardTimerEventListener(updateFn, finishFn);
    }
}

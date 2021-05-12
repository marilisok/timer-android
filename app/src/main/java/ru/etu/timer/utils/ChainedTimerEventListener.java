package ru.etu.timer.utils;

import java.util.function.Consumer;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.dto.TimerData;

public class ChainedTimerEventListener implements TimerEventListener {

    private final Consumer<TimeContainer> updateFn;
    private final Consumer<TimerData> finishFn;

    public ChainedTimerEventListener(Consumer<TimeContainer> updateFn, Consumer<TimerData> finishFn) {
        this.updateFn = updateFn;
        this.finishFn = finishFn;
    }



    @Override
    public void update(TimeContainer currentTime) {
        updateFn.accept(currentTime);
    }

    @Override
    public void finish(TimerData timerData) {
        finishFn.accept(timerData);
    }
}

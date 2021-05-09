package ru.etu.timer.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.dto.TimerData;

public class StandardTimerEventListenerBuilder implements TimerEventListenerBuilder {
    private final List<Consumer<TimeContainer>> updateFunctions;
    private final List<Consumer<TimerData>> finishFunctions;

    public StandardTimerEventListenerBuilder() {
        updateFunctions = new LinkedList<>();
        finishFunctions = new LinkedList<>();
    }

    @Override
    public void onUpdate(Consumer<TimeContainer> currentTimeConsumer) {
        updateFunctions.add(currentTimeConsumer);
    }

    @Override
    public void onFinish(Consumer<TimerData> timerDataConsumer) {
        finishFunctions.add(timerDataConsumer);
    }

    private Consumer<TimeContainer> aggregateUpdateFunctions() {
        return currentTime -> {
            for (Consumer<TimeContainer> function : updateFunctions) {
                function.accept(currentTime);
            }
        };
    }

    private Consumer<TimerData> aggregateFinishFunctions() {
        return timerData -> {
            for (Consumer<TimerData> function : finishFunctions) {
                function.accept(timerData);
            }
        };
    }

    @Override
    public TimerEventListener build() {
        return new StandardTimerEventListener(
                aggregateUpdateFunctions(),
                aggregateFinishFunctions()
        );
    }
}

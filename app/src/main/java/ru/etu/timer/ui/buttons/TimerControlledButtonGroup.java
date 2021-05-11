package ru.etu.timer.ui.buttons;


import androidx.appcompat.app.AppCompatActivity;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.service.timer.TimerFacade;
import ru.etu.timer.ui.pickers.TimeNumberPicker;
import ru.etu.timer.utils.ChainedTimerEventListenerBuilder;
import ru.etu.timer.utils.TimerEventListenerBuilder;
import ru.etu.timer.viewmodel.TimerViewModel;

public class TimerControlledButtonGroup {
    private final TimerStartButton startButton;
    private final TimerContinueButton continueButton;
    private final TimerPauseButton pauseButton;
    private final TimerEndButton endButton;
    private final TimerViewModel timerViewModel;
    private final Storage storage;
    private final Notifier notifier;
    private final TimeNumberPicker picker;

    public TimerControlledButtonGroup(AppCompatActivity context, TimerViewModel timerViewModel, Storage storage, Notifier notifier, TimeNumberPicker picker){
        startButton = new TimerStartButton(context, this);
        pauseButton = new TimerPauseButton(context, this);
        continueButton = new TimerContinueButton(context, this);
        endButton = new TimerEndButton(context, this);
        this.timerViewModel = timerViewModel;
        this.storage = storage;
        this.notifier = notifier;
        this.picker = picker;
        initializeState(timerViewModel.getButtonsState());
    }

    public void startButtonPressed() {
        timerViewModel.setCurrentWorkingTimer(createTimer(new TimeContainer(picker.toSeconds())));
        timerViewModel.setCurrentButtonsState(toggleStart());
        picker.hide();
        timerViewModel.getCurrentWorkingTimer().start();
    }

    public void pauseButtonPressed() {
        timerViewModel.setCurrentButtonsState(togglePause());
        timerViewModel.getCurrentWorkingTimer().pause();
    }

    public void continueButtonPressed() {
        timerViewModel.setCurrentButtonsState(toggleStart());
        timerViewModel.getCurrentWorkingTimer().tcontinue();
    }

    public void endButtonPressed() {
        timerViewModel.setCurrentButtonsState(toggleEnd());
        picker.show();
        timerViewModel.getCurrentWorkingTimer().end();
        timerViewModel.setCurrentTimeOnClock(new TimeContainer(0));
    }


    private State toggleStart() {
        startButton.hide();
        pauseButton.show();
        continueButton.hide();
        endButton.show();
        return State.WORKING;
    }

    private State togglePause() {
        startButton.hide();
        pauseButton.hide();
        continueButton.show();
        endButton.show();
        return State.PAUSED;
    }

    private State toggleEnd() {
        startButton.show();
        pauseButton.hide();
        continueButton.hide();
        endButton.hide();
        return State.INIT;
    }


    Timer createTimer(TimeContainer timeScheduled) {
        TimerEventListenerBuilder eventListenerBuilder = new ChainedTimerEventListenerBuilder();
        eventListenerBuilder.onUpdate(timerViewModel::setCurrentTimeOnClock);
        eventListenerBuilder.onFinish(ignored -> {
            timerViewModel.setCurrentHistory(storage.getHistory());
        });

        return new TimerFacade(
                timeScheduled,
                notifier,
                storage,
                eventListenerBuilder
        );
    }

    public void initializeState(State state) {
        if (state == null) {
            timerViewModel.setCurrentButtonsState(toggleEnd());
            return;
        }

        switch (state) {
            case PAUSED:
                togglePause();
                break;
            case WORKING:
                toggleStart();
        }
    }

    public enum State {
        INIT, WORKING, PAUSED
    }
}

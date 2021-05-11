package ru.etu.timer.ui.buttons;

import android.widget.TimePicker;

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
    }

    public void startButtonPressed() {
        timerViewModel.setCurrentWorkingTimer(createTimer(new TimeContainer(picker.toSeconds())));
        picker.hide();
        startButton.hide();
        pauseButton.show();
        continueButton.hide();
        endButton.show();
        timerViewModel.getCurrentWorkingTimer().start();
    }

    public void pauseButtonPressed() {
        startButton.hide();
        pauseButton.hide();
        continueButton.show();
        endButton.show();
        timerViewModel.getCurrentWorkingTimer().pause();
    }

    public void continueButtonPressed() {
        startButton.hide();
        pauseButton.show();
        continueButton.hide();
        endButton.show();
        timerViewModel.getCurrentWorkingTimer().tcontinue();
    }

    public void endButtonPressed() {
        startButton.show();
        pauseButton.hide();
        continueButton.hide();
        endButton.hide();
        picker.show();
        timerViewModel.getCurrentWorkingTimer().end();
        timerViewModel.setCurrentTimeOnClock(new TimeContainer(0));
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
}

package ru.etu.timer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.ui.buttons.TimerControlledButtonGroup;

public class TimerViewModel extends ViewModel {
    MutableLiveData<TimeContainer> currentTimeOnClock = new MutableLiveData<>();
    MutableLiveData<Timer> currentWorkingTimer = new MutableLiveData<>();
    MutableLiveData<List<TimerData>> currentHistory = new MutableLiveData<>();
    MutableLiveData<TimerControlledButtonGroup.State> buttonsState = new MutableLiveData<>();

    public TimerViewModel() {
        currentTimeOnClock.setValue(new TimeContainer(0));
    }

    public LiveData<TimeContainer> getCurrentTimeOnClockLiveData() {
        return currentTimeOnClock;
    }

    public void setCurrentTimeOnClock(TimeContainer timeContainer) {
        currentTimeOnClock.postValue(timeContainer);
    }

    public void setCurrentWorkingTimer(Timer timer) {
        currentWorkingTimer.setValue(timer);
    }

    public Timer getCurrentWorkingTimer() {
        return currentWorkingTimer.getValue();
    }

    public LiveData<List<TimerData>> getCurrentHistoryLiveData() {
        return currentHistory;
    }

    public void setCurrentHistory(List<TimerData> timerHistory) {
        currentHistory.postValue(timerHistory);
    }

    public void setCurrentButtonsState(TimerControlledButtonGroup.State state) {
        buttonsState.postValue(state);
    }

    public TimerControlledButtonGroup.State getButtonsState() {
        return buttonsState.getValue();
    }
}

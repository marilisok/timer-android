package ru.etu.timer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.service.timer.Timer;

public class TimerViewModel extends ViewModel {
    MutableLiveData<TimeContainer> currentTimeOnClock = new MutableLiveData<>();
    MutableLiveData<Timer> currentWorkingTimer = new MutableLiveData<>();

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

}

package ru.etu.timer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.etu.timer.dto.TimeContainer;

public class TimerViewModel extends ViewModel {
    MutableLiveData<TimeContainer> currentTimeOnClock = new MutableLiveData<>(new TimeContainer(0));

    public LiveData<TimeContainer> getCurrentTimeOnClock() {
        return currentTimeOnClock;
    }

    public void setCurrentTimeOnClock(TimeContainer timeContainer) {
        currentTimeOnClock.postValue(timeContainer);
    }

}

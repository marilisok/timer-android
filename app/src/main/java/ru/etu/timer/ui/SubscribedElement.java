package ru.etu.timer.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import ru.etu.timer.dto.TimeContainer;

public interface SubscribedElement<T> {
    void subscribe(LifecycleOwner owner, LiveData<T> observer);
}

package ru.etu.timer.ui;

import androidx.lifecycle.LiveData;

public interface SubscribedElement {
    void subscribe(LiveData<?> observer);
}

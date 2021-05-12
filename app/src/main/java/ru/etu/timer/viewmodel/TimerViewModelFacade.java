package ru.etu.timer.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class TimerViewModelFacade {
    private final Context context;

    public TimerViewModelFacade(Context context) {
        this.context = context;
    }

    public TimerViewModel get() {
        return new ViewModelProvider((ViewModelStoreOwner) context, new TimerViewModelFactory())
                .get(TimerViewModel.class);
    }
}

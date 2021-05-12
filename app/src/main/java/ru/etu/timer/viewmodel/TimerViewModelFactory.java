package ru.etu.timer.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TimerViewModelFactory implements ViewModelProvider.Factory {
    private static TimerViewModel viewModel;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (viewModel == null)
            viewModel = new TimerViewModel();
        return (T) viewModel;
    }
}

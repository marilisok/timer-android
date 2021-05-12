package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.etu.timer.di.components.DaggerContextComponent;
import ru.etu.timer.di.modules.ContextModule;
import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.notifier.NotificationSender;
import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.ui.buttons.TimerControlledButtonGroup;
import ru.etu.timer.ui.formatters.TimerHistoryFormatter;
import ru.etu.timer.ui.pickers.TimeNumberPicker;
import ru.etu.timer.ui.progressBar.CircularProgressBar;
import ru.etu.timer.ui.textviews.TimerHistoryArea;
import ru.etu.timer.ui.textviews.TimerValueArea;
import ru.etu.timer.viewmodel.TimerViewModel;
import ru.etu.timer.viewmodel.TimerViewModelFacade;

public class MainActivity extends AppCompatActivity {

    @Inject
    Notifier notifier;

    @Inject
    Storage storage;

    @Inject
    NotificationSender sender;

    TimerViewModel timerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeInjection();
        setContentView(R.layout.activity_main);
        sender.createChannel(this);
        timerViewModel = new TimerViewModelFacade(this).get();


        CircularProgressBar progressBar = new CircularProgressBar(this);
        progressBar.subscribe(this, timerViewModel.getCurrentTimeOnClockLiveData());

        TimeNumberPicker timeNumberPicker = new TimeNumberPicker(this);
        TimerControlledButtonGroup timerControlledButtonGroup = new TimerControlledButtonGroup(
                this, timerViewModel, storage, notifier, timeNumberPicker, progressBar
        );

        TimerValueArea textArea = new TimerValueArea(this);
        textArea.subscribe(this, timerViewModel.getCurrentTimeOnClockLiveData());

        TimerHistoryArea timerHistoryArea = new TimerHistoryArea(
                this, storage, new TimerHistoryFormatter()
        );
        timerHistoryArea.subscribe(this, timerViewModel.getCurrentHistoryLiveData());

    }

    private void makeInjection() {
        DaggerContextComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }
}
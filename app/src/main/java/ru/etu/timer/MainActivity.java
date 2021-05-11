package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ru.etu.timer.di.components.DaggerContextComponent;
import ru.etu.timer.di.modules.ContextModule;
import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.notifier.NotificationSender;
import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.service.timer.TimerFacade;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.ui.buttons.TimerControlledButtonGroup;
import ru.etu.timer.ui.pickers.TimeNumberPicker;
import ru.etu.timer.utils.ChainedTimerEventListenerBuilder;
import ru.etu.timer.utils.TimerEventListenerBuilder;
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
        sender.createChannel(this);
        setContentView(R.layout.activity_main);

        TimeNumberPicker timeNumberPicker = new TimeNumberPicker(this);
        timerViewModel = new TimerViewModelFacade(this).get();
        TimerControlledButtonGroup timerControlledButtonGroup = new TimerControlledButtonGroup(
                this, timerViewModel, storage, notifier, timeNumberPicker
        );



        TextView textArea = findViewById(R.id.timerValue);
        this.timerViewModel.getCurrentTimeOnClockLiveData().observe(this, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
        });

        TextView historyArea = findViewById(R.id.history);
        this.timerViewModel.getCurrentHistoryLiveData().observe(this, historyList -> {
            List<String> history = new ArrayList<>();
            historyList.sort((o1, o2) -> (int) (o1.getStartDateTime().toEpochSecond(ZoneOffset.UTC) - o2.getStartDateTime().toEpochSecond(ZoneOffset.UTC)));
            for (TimerData element: historyList) {
                history.add(element.getStartDateTime()
                                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                            + " - " +
                            element.getEndDateTime()
                                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
            }
            historyArea.setText(String.join("\n", history));
        });

    }

    private void makeInjection() {
        DaggerContextComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }
}
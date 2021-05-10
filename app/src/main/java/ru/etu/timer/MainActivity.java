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
    protected void onStart() {
        super.onStart();
        makeInjection();
        sender.createChannel(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerViewModel = new TimerViewModelFacade(this).get();


        NumberPicker hoursPicker = findViewById(R.id.hoursPicker);
        NumberPicker minutesPicker = findViewById(R.id.minutesPicker);
        NumberPicker secondsPicker = findViewById(R.id.secondsPicker);
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
        TextView textArea = findViewById(R.id.timerValue);
        this.timerViewModel.getCurrentTimeOnClockLiveData().observe(this, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
            hoursPicker.setValue(timeContainer.getHours());
            minutesPicker.setValue(timeContainer.getMinutes());
            secondsPicker.setValue(timeContainer.getSeconds());
            hoursPicker.setEnabled(timeContainer.toSeconds() == 0);
            minutesPicker.setEnabled(timeContainer.toSeconds() == 0);
            secondsPicker.setEnabled(timeContainer.toSeconds() == 0);
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

        Button startButton = (Button) findViewById(R.id.startbtn);
        startButton.setOnClickListener(btn -> {
            int time = hoursPicker.getValue() * 3600 + minutesPicker.getValue() * 60 + secondsPicker.getValue();
            Timer timer = createTimer(new TimeContainer(time));
            this.timerViewModel.setCurrentWorkingTimer(timer);
            timer.start();
        });

        Button pauseButton = (Button) findViewById(R.id.pausebtn);
        pauseButton.setOnClickListener(btn -> {
            this.timerViewModel.getCurrentWorkingTimer().pause();
        });

        Button endButton = (Button) findViewById(R.id.endbtn);
        endButton.setOnClickListener(btn -> {
            this.timerViewModel.getCurrentWorkingTimer().end();
            this.timerViewModel.setCurrentTimeOnClock(new TimeContainer(0));
        });

    }

    private TimeContainer getTimeScheduled() {
        return new TimeContainer(5);
    }

    private void makeInjection() {
        DaggerContextComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    protected Timer createTimer(TimeContainer timeScheduled) {
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
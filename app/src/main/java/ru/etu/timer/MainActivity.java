package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import ru.etu.timer.di.components.DaggerContextComponent;
import ru.etu.timer.di.modules.ContextModule;
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

        TextView textArea = findViewById(R.id.timerValue);
        this.timerViewModel.getCurrentTimeOnClockLiveData().observe(this, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
        });

        TextView historyArea = findViewById(R.id.history);
        this.timerViewModel.getCurrentHistoryLiveData().observe(this, historyList -> {
            historyArea.setText(historyList.get(historyList.size() - 1).getId());
        });

        Button startButton = (Button) findViewById(R.id.startbtn);
        startButton.setOnClickListener(btn -> {
            Timer timer = createTimer(getTimeScheduled());
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
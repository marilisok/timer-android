package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import ru.etu.timer.service.notifier.Notifier;
import ru.etu.timer.service.notifier.SimpleOnFinishNotifier;
import ru.etu.timer.ui.storage.SharedPreferencesStorage;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.service.timer.TimerFacade;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.service.notifier.NotificationSender;
import ru.etu.timer.ui.notifications.TimerOnFinishNotifier;
import ru.etu.timer.utils.ChainedTimerEventListenerBuilder;
import ru.etu.timer.utils.TimerEventListenerBuilder;
import ru.etu.timer.viewmodel.TimerViewModel;
import ru.etu.timer.viewmodel.TimerViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        TimerOnFinishNotifier.createChannel(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Storage storage = new SharedPreferencesStorage(this);

        NotificationSender sender = new TimerOnFinishNotifier(this);

        TimerViewModel mView = new ViewModelProvider(this, new TimerViewModelFactory())
                .get(TimerViewModel.class);

        TimeContainer timeScheduled = new TimeContainer(0, 0, 10);

        TextView textArea = findViewById(R.id.timerValue);
        mView.getCurrentTimeOnClockLiveData().observe(this, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
        });

        TextView historyArea = findViewById(R.id.history);
        mView.getCurrentHistoryLiveData().observe(this, historyList -> {
            // needs to be sorted by end datetime
            historyArea.setText(historyList.get(historyList.size() - 1).getId());
        });

        Button startButton = (Button) findViewById(R.id.startbtn);
        startButton.setOnClickListener(btn -> {
            Timer timer = createTimer(timeScheduled, storage, mView, sender);
            mView.setCurrentWorkingTimer(timer);
            timer.start();

        });

        Button pauseButton = (Button) findViewById(R.id.pausebtn);
        pauseButton.setOnClickListener(btn -> {
            mView.getCurrentWorkingTimer().pause();
        });

        Button endButton = (Button) findViewById(R.id.endbtn);
        endButton.setOnClickListener(btn -> {
            mView.getCurrentWorkingTimer().end();
            mView.setCurrentTimeOnClock(new TimeContainer(0));
        });

    }

    protected Timer createTimer(TimeContainer timeScheduled, Storage storage, TimerViewModel mView, NotificationSender sender) {
        TimerEventListenerBuilder eventListenerBuilder = new ChainedTimerEventListenerBuilder();
        eventListenerBuilder.onUpdate(mView::setCurrentTimeOnClock);
        eventListenerBuilder.onFinish(ignored -> {
            mView.setCurrentHistory(storage.getHistory());
        });
        Notifier notifier = new SimpleOnFinishNotifier(sender);

        return new TimerFacade(
                timeScheduled,
                notifier,
                storage,
                eventListenerBuilder
        );
    }

}
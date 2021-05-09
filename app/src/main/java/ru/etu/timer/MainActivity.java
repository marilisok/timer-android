package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
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

        TextView textArea = findViewById(R.id.tw);
        mView.getCurrentTimeOnClockLiveData().observe(this, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
        });

        Button startButton = (Button) findViewById(R.id.startbtn);
        startButton.setOnClickListener(btn -> {
            new Thread(() -> startTimer(storage, mView, sender)).start();
        });
    }

    protected void startTimer(Storage storage, TimerViewModel mView, NotificationSender sender) {
        TimerEventListenerBuilder eventListenerBuilder = new ChainedTimerEventListenerBuilder();
        eventListenerBuilder.onUpdate(mView::setCurrentTimeOnClock);
        Notifier notifier = new SimpleOnFinishNotifier(sender);

        Timer timer = new TimerFacade(new TimeContainer(0, 0, 5),
                notifier,
                storage,
                eventListenerBuilder
        );
        timer.start();
    }

}
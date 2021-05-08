package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import ru.etu.timer.service.storage.SharedPreferencesStorage;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.service.timer.TimerFacade;
import ru.etu.timer.utils.StandardTimerEventListener;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.utils.StandardTimerEventListenerBuilder;
import ru.etu.timer.utils.TimerEventListener;
import ru.etu.timer.utils.TimerEventListenerBuilder;
import ru.etu.timer.viewmodel.TimerViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        SharedPreferences sh = context.getSharedPreferences("TimerHistory", Context.MODE_PRIVATE);
        TimerViewModel mView = new ViewModelProvider(this).get(TimerViewModel.class);
        TextView textArea = findViewById(R.id.tw);
        mView.getCurrentTimeOnClock().observe(this, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
        });

        TimerEventListenerBuilder eventListenerBuilder = new StandardTimerEventListenerBuilder();
        eventListenerBuilder.onUpdate(mView::setCurrentTimeOnClock);

        Timer timer = new TimerFacade(new TimeContainer(0, 0, 20),
                System.out::println,
                new SharedPreferencesStorage(sh),
                eventListenerBuilder
        );
        timer.start();
    }

}
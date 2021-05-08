package ru.etu.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.storage.SharedPreferencesStorage;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.service.timer.TimerFacade;
import ru.etu.timer.utils.TimeContainer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        SharedPreferences sh = context.getSharedPreferences("TimerHistory", Context.MODE_PRIVATE);
        Storage storage = new SharedPreferencesStorage(sh);
        TextView tvId = (TextView) findViewById(R.id.tw);
        Timer timer = new TimerFacade(new TimeContainer(3),
                System.out::println, storage);
        timer.start();
    }

}
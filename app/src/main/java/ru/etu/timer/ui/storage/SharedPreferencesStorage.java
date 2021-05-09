package ru.etu.timer.ui.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.storage.Storage;

public class SharedPreferencesStorage implements Storage {
    private final SharedPreferences preferences;
    private final static String PREFERENCES_ID = "TimerHistory";
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.ui.storage.SharedPreferencesStorage");

    public SharedPreferencesStorage(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_ID, Context.MODE_PRIVATE);
        LOGGER.info("SharedPreferencesStorage has been created");
    }

    @Override
    public boolean save(TimerData timerData) {
        SharedPreferences.Editor editor = preferences.edit();
        try {
            String jsonRepresentation = timerData.toJsonString();
            LOGGER.info(String.format("Saving %s", jsonRepresentation));
            editor.putString(timerData.getId(), jsonRepresentation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return editor.commit();
    }

    @Override
    public List<TimerData> getHistory() {
        LOGGER.info("Retrieving history");
        return preferences.getAll().values().stream()
                .map(value -> {
                    try {
                        return TimerData.fromJsonString((String) value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean deleteHistoryRecordById(String id) {
        LOGGER.info(String.format("Deleting history record with id %s", id));
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(id);
        return editor.commit();
    }
}

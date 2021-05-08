package ru.etu.timer.service.storage;

import android.content.SharedPreferences;

import org.json.JSONException;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ru.etu.timer.dto.TimerData;

public class SharedPreferencesStorage implements Storage {
    private final SharedPreferences preferences;
    private final Logger LOGGER = Logger.getLogger("ru.etu.timer.service.storage.SharedPreferencesStorage");

    public SharedPreferencesStorage(SharedPreferences preferences) {
        this.preferences = preferences;
        LOGGER.info("SharedPreferencesStorage has been created");
    }

    @Override
    public boolean save(TimerData timerData) {
        SharedPreferences.Editor editor = preferences.edit();
        try {
            String jsonRepr = timerData.toJsonString();
            LOGGER.info("Saving " + jsonRepr);
            editor.putString(timerData.getId(), jsonRepr);
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

    @Override
    public boolean deleteHistoryRecordById(String id) {
        LOGGER.info("Deleting history record with id " + id);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(id);
        return editor.commit();
    }
}

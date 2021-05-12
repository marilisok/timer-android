package ru.etu.timer.service.storage;

import ru.etu.timer.dto.TimerData;

import java.util.List;

public interface Storage {
    boolean save(TimerData timerData);

    List<TimerData> getHistory();

    boolean deleteHistoryRecordById(String id);
}

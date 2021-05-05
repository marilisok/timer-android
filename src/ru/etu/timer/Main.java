package ru.etu.timer;

import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.service.timer.Timer;
import ru.etu.timer.service.timer.TimerFacade;
import ru.etu.timer.utils.TimeContainer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Timer timer = new TimerFacade(new TimeContainer(5), data -> System.out.println("notifier"), new Storage() {
            @Override
            public boolean save(TimerData timerData) {
                System.out.println("Save " + timerData.getId());
                return false;
            }

            @Override
            public List<TimerData> getHistory() {
                return null;
            }

            @Override
            public boolean deleteHistoryRecordById(String id) {
                return false;
            }
        });
        timer.start();
    }
}

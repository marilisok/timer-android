package ru.etu.timer;

import ru.etu.timer.service.StandardTimerImpl;
import ru.etu.timer.service.Timer;

public class Main {
    public static void main(String[] args) {
        Timer timer = new StandardTimerImpl(5);
        timer.start();
    }
}

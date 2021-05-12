package ru.etu.timer.ui.formatters;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import ru.etu.timer.dto.TimerData;

public class TimerHistoryFormatter {

    public String format(TimerData timerData) {
        return String.format("%s - %s",
                timerData.getStartDateTime()
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                timerData.getEndDateTime()
                        .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))
        );

    }
}

package ru.etu.timer.ui.textviews;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.etu.timer.R;
import ru.etu.timer.dto.TimerData;
import ru.etu.timer.service.storage.Storage;
import ru.etu.timer.ui.SubscribedElement;
import ru.etu.timer.ui.formatters.TimerHistoryFormatter;

public class TimerHistoryArea implements SubscribedElement<List<TimerData>> {
    private final TextView historyArea;
    private final TimerHistoryFormatter formatter;

    public TimerHistoryArea(AppCompatActivity context, Storage storage, TimerHistoryFormatter formatter) {
        historyArea = context.findViewById(R.id.history);
        this.formatter = formatter;
        setHistory(storage.getHistory());
    }

    @Override
    public void subscribe(LifecycleOwner owner, LiveData<List<TimerData>> observer) {
        observer.observe(owner, this::setHistory);
    }

    protected void setHistory(List<TimerData> historyList) {
        historyArea.setText(
                historyList.stream()
                        .sorted((o1, o2) -> (int) (o2.getStartDateTime().toEpochSecond(ZoneOffset.UTC)
                                - o1.getStartDateTime().toEpochSecond(ZoneOffset.UTC)))
                        .map(formatter::format)
                        .collect(Collectors.joining("\n")
                        )
        );
    }
}

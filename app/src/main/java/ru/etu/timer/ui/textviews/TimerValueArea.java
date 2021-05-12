package ru.etu.timer.ui.textviews;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import ru.etu.timer.R;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.ui.SubscribedElement;

public class TimerValueArea implements SubscribedElement<TimeContainer> {
    private final TextView textArea;

    public TimerValueArea(AppCompatActivity context) {
        this.textArea = context.findViewById(R.id.timerValue);
    }

    @Override
    public void subscribe(LifecycleOwner owner, LiveData<TimeContainer> observer) {
        observer.observe(owner, timeContainer -> {
            textArea.setText(timeContainer.toFormattedString());
        });
    }
}

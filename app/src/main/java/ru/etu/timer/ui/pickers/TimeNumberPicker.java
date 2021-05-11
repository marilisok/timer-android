package ru.etu.timer.ui.pickers;

import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import ru.etu.timer.R;

public class TimeNumberPicker {
    private final NumberPicker hoursPicker;
    private final NumberPicker minutesPicker;
    private final NumberPicker secondsPicker;

    public TimeNumberPicker(AppCompatActivity context) {
        hoursPicker = context.findViewById(R.id.hoursPicker);
        minutesPicker = context.findViewById(R.id.minutesPicker);
        secondsPicker = context.findViewById(R.id.secondsPicker);
        setBoundaryValues();
        setDefault();
    }

    private void setBoundaryValues() {
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
    }

    public void setDefault() {
        hoursPicker.setValue(0);
        minutesPicker.setValue(0);
        secondsPicker.setValue(0);
    }

    public int toSeconds() {
        return hoursPicker.getValue() * 3600 + minutesPicker.getValue() * 60 + secondsPicker.getValue();
    }

    public void hide() {
        hoursPicker.setVisibility(View.INVISIBLE);
        minutesPicker.setVisibility(View.INVISIBLE);
        secondsPicker.setVisibility(View.INVISIBLE);
    }

    public void show() {
        hoursPicker.setVisibility(View.VISIBLE);
        minutesPicker.setVisibility(View.VISIBLE);
        secondsPicker.setVisibility(View.VISIBLE);
    }
}

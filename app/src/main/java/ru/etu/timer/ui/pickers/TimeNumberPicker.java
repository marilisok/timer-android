package ru.etu.timer.ui.pickers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import ru.etu.timer.R;

public class TimeNumberPicker {
    private final NumberPicker hoursPicker;
    private final NumberPicker minutesPicker;
    private final NumberPicker secondsPicker;
    private final ViewGroup pickerGroup;

    public TimeNumberPicker(AppCompatActivity context) {
        hoursPicker = context.findViewById(R.id.hoursPicker);
        minutesPicker = context.findViewById(R.id.minutesPicker);
        secondsPicker = context.findViewById(R.id.secondsPicker);
        pickerGroup = context.findViewById(R.id.pickerGroup);
        setBoundaryValues();
        setDefault();
        show();
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
        pickerGroup.setVisibility(View.INVISIBLE);
    }

    public void show() {
        pickerGroup.setVisibility(View.VISIBLE);
    }
}

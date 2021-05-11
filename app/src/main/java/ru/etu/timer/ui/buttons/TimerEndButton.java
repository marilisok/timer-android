package ru.etu.timer.ui.buttons;

import androidx.appcompat.app.AppCompatActivity;

import ru.etu.timer.R;

public class TimerEndButton extends TimerBaseButton {

    public TimerEndButton(AppCompatActivity context, TimerControlledButtonGroup observer) {
        super(context, observer, R.id.endbtn, context.getString(R.string.endTimer));
    }

    @Override
    public void notifyController() {
        observer.endButtonPressed();
    }
}
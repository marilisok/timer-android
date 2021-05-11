package ru.etu.timer.ui.buttons;

import androidx.appcompat.app.AppCompatActivity;

import ru.etu.timer.R;

public class TimerPauseButton extends TimerBaseButton {

    public TimerPauseButton(AppCompatActivity context, TimerControlledButtonGroup observer) {
        super(context, observer, R.id.pausebtn, context.getString(R.string.pauseTimer));
    }

    @Override
    public void notifyController() {
        observer.pauseButtonPressed();
    }
}

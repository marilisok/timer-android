package ru.etu.timer.ui.buttons;

import androidx.appcompat.app.AppCompatActivity;

import ru.etu.timer.R;

public class TimerStartButton extends TimerBaseButton {

    public TimerStartButton(AppCompatActivity context, TimerControlledButtonGroup observer) {
        super(context, observer, R.id.startbtn, context.getString(R.string.startTimer));
    }

    @Override
    public void notifyController() {
        observer.startButtonPressed();
    }
}

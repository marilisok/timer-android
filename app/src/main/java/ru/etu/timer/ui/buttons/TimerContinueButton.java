package ru.etu.timer.ui.buttons;

import androidx.appcompat.app.AppCompatActivity;

import ru.etu.timer.R;

public class TimerContinueButton extends TimerBaseButton {

    public TimerContinueButton(AppCompatActivity context, TimerControlledButtonGroup observer) {
        super(context, observer, R.id.continuebtn, context.getString(R.string.continueTimer));
    }

    @Override
    public void notifyController() {
        observer.continueButtonPressed();
    }
}

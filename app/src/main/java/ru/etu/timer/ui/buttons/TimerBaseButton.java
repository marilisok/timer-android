package ru.etu.timer.ui.buttons;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.util.logging.Logger;

import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.ui.SubscribedElement;

public abstract class TimerBaseButton implements ControlledButton, SubscribedElement<TimeContainer> {
    protected static int SELECTOR;
    protected static String NAME;
    protected final TimerControlledButtonGroup observer;
    protected final Button button;
    protected final Logger LOGGER;

    public TimerBaseButton(AppCompatActivity context, TimerControlledButtonGroup observer, int selector, String name) {
        SELECTOR = selector;
        NAME = name;
        this.observer = observer;
        button = (Button) context.findViewById(SELECTOR);
        LOGGER = Logger.getLogger("ru.etu.timer.ui.buttons");
        registerButtonListeners();
    }

    private void registerButtonListeners() {
        button.setOnClickListener(
                btn -> {
                    LOGGER.info(String.format("%s button has been clicked", NAME));
                    notifyController();
                }
        );
    }

    public void hide() {
        LOGGER.info(String.format("%s button has been hidden", NAME));
        button.setVisibility(View.INVISIBLE);
    }

    public void show() {
        LOGGER.info(String.format("%s button has been shown", NAME));
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyController() {
        observer.pauseButtonPressed();
    }

    @Override
    public void subscribe(LifecycleOwner owner, LiveData<TimeContainer> observer) {
    }
}

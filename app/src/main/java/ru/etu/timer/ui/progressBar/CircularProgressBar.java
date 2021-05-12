package ru.etu.timer.ui.progressBar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import ru.etu.timer.R;
import ru.etu.timer.dto.TimeContainer;
import ru.etu.timer.ui.SubscribedElement;

public class CircularProgressBar implements SubscribedElement<TimeContainer> {

    final private ProgressBar progressBarView;
    final private ViewGroup progressBarGroup;
    private int timerTime;

    public CircularProgressBar(AppCompatActivity context) {
        progressBarView = context.findViewById(R.id.view_progress_bar);
        progressBarGroup = context.findViewById(R.id.progressBar);
        timerTime = 0;
    }

    @Override
    public void subscribe(LifecycleOwner owner, LiveData<TimeContainer> observer) {
        observer.observe(owner, timeContainer -> {
            setProgress(timeContainer.toSeconds());
        });
    }

    public void setTimerTime(int timerTime) {
        this.timerTime = timerTime;
    }

    public void setProgress(int currentTimeOnClock) {
        progressBarView.setMax(timerTime);
        progressBarView.setSecondaryProgress(timerTime);
        progressBarView.setProgress(currentTimeOnClock);
    }

    public void show() {
        progressBarGroup.setVisibility(View.VISIBLE);
    }

    public void hide() {
        progressBarGroup.setVisibility(View.INVISIBLE);
    }


}

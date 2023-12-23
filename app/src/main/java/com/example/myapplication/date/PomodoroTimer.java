package com.example.myapplication.date;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class PomodoroTimer {
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private int pomodoroTime = 25 * 60 * 1000; // 25 minutes in milliseconds  
    private int breakTime = 5 * 60 * 1000; // 5 minutes in milliseconds  

    public PomodoroTimer(TextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            countDownTimer = new CountDownTimer(pomodoroTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(formatTime(millisUntilFinished));
                    updateDisplay();
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("Done!");
                    reset();
                }
            }.start();
        }
    }

    public void reset() {
        if (isRunning) {
            countDownTimer.cancel();
            timerTextView.setText("00:00");
            isRunning = false;
        }
    }

    public void pause() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private String formatTime(long time) {
        int minutes = (int) (time / (1000 * 60));
        int seconds = (int) ((time % (1000 * 60)) / 1000);
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateDisplay() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                timerTextView.invalidate(); // Redraw the view to update the text color change.  
            }
        });
    }
}
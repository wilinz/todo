package com.example.myapplication.list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myapplication.R;

public class Focus_activity extends AppCompatActivity {

    private CountDownTimer timer;
    private ProgressBar progressBar;
    private ImageView start;
    private ImageView pause;
    private ImageView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        start = findViewById(R.id.startButton);

        cancel = findViewById(R.id.cancelButton);

        // 创建一个新的倒计时器对象
        timer = new CountDownTimer(1500000, 1000) { // 25分钟倒计时，每秒更新一次
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((1500000 - millisUntilFinished) * 100 / 1500000); // 计算进度
//                我们计算剩余时间占总时间的百分比，并将其用作进度条的进度值，从而实现随着时间变化而更新的效果。
                progressBar.setProgress(progress); // 更新进度条
            }

            @Override
            public void onFinish() {
                // 倒计时结束时的逻辑
            }
        };

        start.setOnClickListener(v -> {
            // 开始倒计时
            timer.start();
        });

        cancel.setOnClickListener(v -> {
            // 取消倒计时
            timer.cancel();
        });

    }
//
//
//
//    public void pause() {
//        mMillisUntilFinished = mStopTimeInFuture - SystemClock.elapsedRealtime();
//        isRunning = false;
//        mPaused = true;
//    }
//
//    public long resume() {
//        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisUntilFinished;
//        mHandler.sendMessage(mHandler.obtainMessage(MSG));
//        isRunning = true;
//        mPaused = false;
//        return mMillisUntilFinished;
//    }
//
//    public void cancel() {
//        mHandler.removeMessages(MSG);
//        isRunning = false;
//    }
//
//    public void onFinish() {
//         Add your logic for what should happen when the countdown finishes
//    }
//
//    public void onTick(long millisUntilFinished) {
//         Add your logic for what should happen on each countdown interval
//    }
//
//
//    private void onClick(View v) {
//         暂停倒计时
//        timer.wait();
//    }
}
package com.example.myapplication.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity {
    private static final long MSG_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // 传递标志
                intent.putExtra(LoginActivity.isOpenFromMainActivityKey, true);
                startActivity(intent);
                finish();
            }
        };

        handler.postDelayed(runnable, MSG_TIME);
    }
}
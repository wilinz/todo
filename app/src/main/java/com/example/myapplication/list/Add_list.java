package com.example.myapplication.list;

import static com.google.android.material.datepicker.MaterialDatePicker.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.date.Content;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;


public class Add_list extends AppCompatActivity {

    private ImageView endDate;
    private TextView dateTextView;
    private EditText describes1;
    private EditText title1;
    private ImageView colorPicker;
//    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        dateTextView = findViewById(R.id.dateTextView);
        describes1 = (EditText) findViewById(R.id.describes_1);
        title1 = (EditText) findViewById(R.id.title_1);
        endDate = findViewById(R.id._time);

        Content oldData = getIntent().getParcelableExtra("con");
        boolean isUpdate = (oldData != null);
        if (isUpdate) {
            title1.setText(oldData.getTitle());
            describes1.setText(oldData.getDescribes());
            dateTextView.setText(oldData.getDate());
        }

        endDate.setOnClickListener(this::onClick);


        ImageView save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            Content content;
            if (isUpdate) {
                content = oldData;
            } else {
                content = new Content();
            }

            content.setDescribes(describes1.getText().toString());
            content.setTitle(title1.getText().toString());
            content.setDate(dateTextView.getText().toString());
            if (isUpdate){
                content.update(content.getId());
            }else {
                content.save();
            }
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();


        });

        colorPicker = findViewById(R.id.sort);

    }

    public void onClick(View v) {
        // 创建日期范围选择器并设置标题为 "Select dates"

        Builder<Pair<Long, Long>> pairBuilder = Builder.dateRangePicker();
        MaterialDatePicker<Pair<Long, Long>> picker = pairBuilder
                //todo
                //.setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build();


        // 显示日期范围选择器
        picker.show(getSupportFragmentManager(), picker.toString());


        // 添加对积极按钮点击的响应
        picker.addOnPositiveButtonClickListener(selection -> {
            // 响应积极按钮点击

            LocalDate startData = LocalDateTime.ofInstant(Instant.ofEpochMilli(selection.first), ZoneId.systemDefault()).toLocalDate();
            LocalDate endData = LocalDateTime.ofInstant(Instant.ofEpochMilli(selection.second), ZoneId.systemDefault()).toLocalDate();

            if (endDate.equals(LocalDate.now())) {
                //  最后一天的提醒逻辑
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("最后一天");
                builder.setMessage("今天是期限的最后一天！");
                builder.setPositiveButton("确定", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            // 创建时间选择器对象
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Appointment time")
                    //todo
//                    .setTheme(R.style.ThemeOverlay_App_TimePicker )
                    .build();


            timePicker.show(getSupportFragmentManager(), "tag");

            timePicker.addOnPositiveButtonClickListener(v1 -> {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                LocalDateTime startDataTime = startData.atTime(hour, minute);
                LocalDateTime endDataTime = endData.atTime(hour, minute);

                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String startDateText = startDataTime.format(pattern);
                String endDateText = endDataTime.format(pattern);


                dateTextView.setText(startDateText + " -> " + endDateText);
            });


        });

// 添加对消极按钮点击的响应
        picker.addOnNegativeButtonClickListener((dialog) -> {
            // 响应消极按钮点击
        });

    }


}
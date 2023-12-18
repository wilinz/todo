package com.example.myapplication.list;

import static com.google.android.material.datepicker.MaterialDatePicker.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.PinnedComparator;
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
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;


public class Add_list extends AppCompatActivity {

    private ImageView endDate;
    private TextView dateTextView;
    private EditText describes1;
    private EditText title1;
    private ImageView sortPicker;
    private ImageView pinned;
    private NotificationManager manager;
    private String channelID;
    private CharSequence channelNAME;
    private List<Content> list;
    private String category = "未分类";
    private Content content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        dateTextView = findViewById(R.id.dateTextView);
        describes1 = (EditText) findViewById(R.id.describes_1);
        title1 = (EditText) findViewById(R.id.title_1);
        endDate = findViewById(R.id._time);
        pinned = findViewById(R.id.pinned);
        sortPicker = findViewById(R.id.sort);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        Content oldData = getIntent().getParcelableExtra("con");
//        具体来说，getIntent() 用于获取启动当前 Activity 的 Intent 对象，
//        然后 getParcelableExtra("con") 从该 Intent 中获取名为 "con" 的
//        Parcelable 类型数据。在这个场景中，"con" 可能是前一个 Activity 中放
//        置的一个 Content 对象，它实现了 Parcelable 接口以便能够在不同的 Activity 之间传递。
        boolean isUpdate = (oldData != null);
        
       if (isUpdate) {
                content = oldData;
        } else {
                content = new Content();
        }
        if (isUpdate) {
            category = oldData.getCategory();
            title1.setText(oldData.getTitle());
            describes1.setText(oldData.getDescribes());
            dateTextView.setText(oldData.getDate());
        }

        endDate.setOnClickListener(this::onClick);
        sortPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Add_list.this,v);
                popupMenu.getMenuInflater().inflate(R.menu.activity_sort_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem menuItem ){
                        if(menuItem.getItemId()==R.id.study ){
                            Toast.makeText(Add_list.this, "学习", Toast.LENGTH_SHORT).show();
                            category = "学习";
                        }
                        if(menuItem.getItemId() == R.id.life ){
                            Toast.makeText(Add_list.this, "生活", Toast.LENGTH_SHORT).show();
                            category = "生活";
                        }
                        if(menuItem.getItemId() == R.id.work ){
                            Toast.makeText(Add_list.this, "工作", Toast.LENGTH_SHORT).show();
                            category = "工作";
                        }
                        if(menuItem.getItemId() == R.id.family ){
                            Toast.makeText(Add_list.this, "家庭", Toast.LENGTH_SHORT).show();
                            category = "家庭";
                        }

                        return true;
                    }

                });
                popupMenu.show();
            }
        });

        ImageView save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            content.setDescribes(describes1.getText().toString());
            content.setTitle(title1.getText().toString());
            content.setDate(dateTextView.getText().toString());
            content.setCategory(category);
            if (isUpdate){
                content.update(content.getId());
            }else {
                content.save();
            }
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();

        });





    }




    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, channelName, importance);
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }



    public void onClick(View v) {
        MaterialDatePicker.Builder<Long> pairBuilder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> picker = pairBuilder
                .setSelection(System.currentTimeMillis())
                .build();


        picker.show(getSupportFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(selection -> {
//           Content content = new Content();
//            Content previousContent = getContentFromPreviousContext();
            LocalDate Date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selection), ZoneId.systemDefault()).toLocalDate();

            if (Date.equals(LocalDate.now())) {
                createNotificationChannel("1","截至日期通知",NotificationManager.IMPORTANCE_HIGH);
                Notification notificationGet = new NotificationCompat.Builder(this, "subscribe")
                        .setAutoCancel(true)
                        .setContentTitle("截至日期通知")
                        .setContentText("您有一条代办事项日期即将截止")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .build();
                manager.notify(1, notificationGet);

            } else if (Date.isBefore(LocalDate.now())) {
                content.setOver(true);
            } else if (Date.isAfter(LocalDate.now())) {
                content.setOver(false);
                content.setToDefault("isOver");
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
                LocalDateTime DataTime = Date.atTime(hour, minute);
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String DateText = DataTime.format(pattern);
//                String endDateText = endDataTime.format(pattern);
                dateTextView.setText(DateText);
            });
        });
        picker.addOnNegativeButtonClickListener((dialog) -> {
            // 响应消极按钮点击
        });
    }

//    public abstract class Context {
//        public static final String NOTIFICATION_SERVICE = "notification";
//    }


}

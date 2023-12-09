package com.example.myapplication.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.date.User;
import com.example.myapplication.list.ModalBottomSheet;

import org.litepal.LitePal;

import java.util.List;

public class Changed_password extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_password);

        imageView = findViewById(R.id.pol_2);
        textView = findViewById(R.id.finish_2);
        editText = findViewById(R.id.edit_password);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Changed_password.this, ModalBottomSheet.class);
                startActivity(intent);
            }
        });


        String repassword = editText.getText().toString() ;
        List<User> users = LitePal.findAll(User.class);
        for (User user : users) {
            if (user.getUsername().equals(repassword) ) {
                Toast.makeText(Changed_password.this, "已存在该密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        User user = new User();
        user.setUsername(repassword);
        user.save();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Changed_password.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Changed_password.this, ModalBottomSheet.class);
                startActivity(intent);
            }
        });

    }
    }

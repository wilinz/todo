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
import com.example.myapplication.list.ListActivity;
import com.example.myapplication.list.ModalBottomSheet;
import com.example.myapplication.loginandregister.LoginActivity;
import com.example.myapplication.loginandregister.RegisterActivity;

import org.litepal.LitePal;

import java.util.List;

public class Changed_username extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_username);

        imageView = findViewById(R.id.pol_1);
        textView = findViewById(R.id.finish_1);
        editText = findViewById(R.id.edit_username);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Changed_username.this,ModalBottomSheet.class);
                startActivity(intent);
            }
        });


        String reusername = editText.getText().toString() ;
        List<User> users = LitePal.findAll(User.class);
        for (User user : users) {
            if (user.getUsername().equals(reusername) ) {
                Toast.makeText(Changed_username.this, "已存在该名称", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        User user = new User();
        user.setUsername(reusername);
        user.save();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Changed_username.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Changed_username.this, ModalBottomSheet.class);
                startActivity(intent);
            }
        });

    }




}
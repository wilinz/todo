package com.example.myapplication.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.date.User;

import org.litepal.LitePal;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUsername;
    private EditText registerPassword;

    private EditText reregisterPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         ImageView imageView = (ImageView) findViewById(R.id.pol);
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                 startActivity(intent);
             }
         });
        Button btn_4 = findViewById(R.id.btn_4 );
        registerUsername = findViewById(R.id.name1);
        registerPassword = findViewById(R.id.code1);
        reregisterPassword = findViewById(R.id.code2);

        btn_4.setOnClickListener(v -> {
            String username = registerUsername.getText().toString() ;
            String password = registerPassword.getText().toString();
            String repassword = reregisterPassword.getText().toString() ;
            if (username.equals(" ") || password.equals(" ") || repassword.equals(" ")){
                Toast.makeText(RegisterActivity.this, "账号或密码为空", Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> users = LitePal.findAll(User.class);
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    Toast.makeText(RegisterActivity.this, "账号不可以重复注册", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRepassword(repassword);
            user.save();

            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);

        });
        

    }

}
package com.example.myapplication.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.date.User;
import com.example.myapplication.list.ListActivity;

import org.litepal.LitePal;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private EditText loginName;

    private EditText loginPassword;
    private boolean isHide = false;
    private ImageView displayPassword;
    private Button login;
    private CheckBox checkBox;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // isOpenFromMainActivity 的 key
    public static String isOpenFromMainActivityKey = "isOpenFromMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // 判断是否是从Main启动
        boolean isOpenFromMainActivity = getIntent().getBooleanExtra(isOpenFromMainActivityKey,false);

        TextView textView = findViewById(R.id.btn_5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginName = findViewById(R.id.dl_name);
        loginPassword = findViewById(R.id.dl_code);
        displayPassword = findViewById(R.id.display_password);
        login = findViewById(R.id.btn_2);
        checkBox = findViewById(R.id.checkbox_password);

//        String username1 = getIntent().getParcelableExtra("username");
//        String password1 = getIntent().getParcelableExtra("password");
//        boolean reLogin =( (username1 != null) && (password1 != null));
//        if(reLogin){
//            loginName.setText(username1);
//            loginPassword.setText(password1);
//        }else{
//            pref = PreferenceManager.getDefaultSharedPreferences(this);
//            boolean isLoggedIn = pref.getBoolean("is_logged_in", false);
            User user = User.getSignedInUser();
//            boolean isRemember = pref.getBoolean("remember_password", false);
            if (user !=null && user.isRemember()) {
                loginName.setText(user.getUsername());
                loginPassword.setText(user.getPassword());
                checkBox.setChecked(true);
            }
            // 从Main启动可以直接判断并进入主活动
            if (user != null && isOpenFromMainActivity) {
                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }

            displayPassword.setOnClickListener(this::onClick);
            displayPassword.setImageResource(R.drawable.show);
            login.setOnClickListener(this::onLoginClick);
//        }

    }

    public void onLoginClick(View v) {
//        User user =LitePal.findLast(User.class);
        String username = loginName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "账号或密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = LitePal.where("username = ?", username).findFirst(User.class);

        if (user == null) {
            Toast.makeText(LoginActivity.this, "账号未注册！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!user.getPassword().equals(password)) {
            Toast.makeText(LoginActivity.this, "密码不正确！", Toast.LENGTH_SHORT).show();
        } else {
            user.setLogin(true);
            user.setRemember(checkBox.isChecked());
            user.update(user.getId());

            ContentValues values = new ContentValues();
            values.put("islogin", false);
            LitePal.updateAll(User.class, values, "username <> ?", username);

            // 不再使用
//            editor = pref.edit();
//            editor.putBoolean("is_logged_in", true);
//            editor.apply();
//
//            editor = pref.edit();
//            if (checkBox.isChecked()) {
//                editor.putBoolean("remember_password", true);
//            } else {
//                editor.clear();
//            }
//            editor.apply();



            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
            startActivity(intent);
        }



    }

    public void onClick(View v) {

        if (v.getId() == R.id.display_password) {
            if (isHide) {
                displayPassword.setImageResource(R.drawable.hidden);
                TransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                loginPassword.setTransformationMethod(method1);
                isHide = false;
            } else {
                displayPassword.setImageResource(R.drawable.show);
                TransformationMethod method = PasswordTransformationMethod.getInstance();
                loginPassword.setTransformationMethod(method);
                isHide = true;
            }
            int index = loginPassword.getText().toString().length();
            loginPassword.setSelection(index);
        }

    }


}
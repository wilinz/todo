package com.example.myapplication.date;
import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class User extends LitePalSupport {

    private long id;
    private String repassword;
    private String  password;
    private boolean isLogin;

    private boolean isRemember = false;

    // 关联 Content 表，多对一
    public List<Content> contents;

    @Column(unique = true, index = true)
    private  String username;



    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String  getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public static User getSignedInUser(){
        return LitePal.where("islogin = ?", "1").findFirst(User.class);
    }
}


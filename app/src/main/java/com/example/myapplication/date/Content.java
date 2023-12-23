package com.example.myapplication.date;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

public class Content extends LitePalSupport implements Parcelable {
//实现接口
    private long id;
    private String title;
    private String describes;
    private String date;
    private boolean isPinned = false;
    private boolean isFinish = false;
    private boolean isOver = false;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;



    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }



    ///////////////////
    protected Content(Parcel in) {
        id = in.readLong();
        title = in.readString();
        describes = in.readString();
        date = in.readString();
        isPinned = in.readByte() != 0;
        isFinish = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(describes);
        dest.writeString(date);
        dest.writeByte((byte) (isPinned ? 1 : 0));
        dest.writeByte((byte) (isFinish ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public Content() {
    }

    //////////////////////

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 与 User 表关联
    public User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", describes='" + describes + '\'' +
                ", date='" + date + '\'' +
                ", isPinned=" + isPinned +
                ", isFinish=" + isFinish +
                ", isOver=" + isOver +
                ", category='" + category + '\'' +
                ", user=" + user +
                '}';
    }
}

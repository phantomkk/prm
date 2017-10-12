package com.project.barcodechecker.models;

import android.text.method.DateTimeKeyListener;

import java.util.Date;

/**
 * Created by lucky on 12-Oct-17.
 */

public class Comment {
    private String name;
    private String avatar;
    private String comment;
    private Date date;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

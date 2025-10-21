package com.example.todoapp;

import java.util.Date;
import java.util.UUID;

public class Task {
    private final UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mDone;

    public Task() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mTitle = "";
        mDone = false;
    }

    public UUID getId() { return mId; }

    public String getTitle() { return mTitle; }
    public void setTitle(String title) { this.mTitle = title; }

    public Date getDate() { return mDate; }
    public void setDate(Date date) { this.mDate = date; }

    public boolean isDone() { return mDone; }
    public void setDone(boolean done) { this.mDone = done; }
}
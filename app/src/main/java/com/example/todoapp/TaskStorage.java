package com.example.todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskStorage {
    private static TaskStorage sTaskStorage;

    private final Context mAppContext;
    private final List<Task> mTasks;

    private TaskStorage(Context context) {
        mAppContext = context.getApplicationContext();
        mTasks = new ArrayList<>();

        for (int i = 1; i <= 150; i++) {
            Task t = new Task();
            t.setTitle("Zadanie #" + i);
            t.setDone(i % 3 == 0);
            mTasks.add(t);
        }
    }

    public static TaskStorage get(Context context) {
        if (sTaskStorage == null) {
            sTaskStorage = new TaskStorage(context);
        }
        return sTaskStorage;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(UUID id) {
        for (Task t : mTasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }
}
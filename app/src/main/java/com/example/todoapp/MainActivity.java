package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {
    private static final String EXTRA_TASK_ID = "com.example.todoapp.task_id";

    public static Intent newIntent(Context context, UUID taskId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID taskId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            taskId = getIntent().getSerializableExtra(EXTRA_TASK_ID, UUID.class);
        } else {
            taskId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        }
        return TaskFragment.newInstance(taskId);
    }
}
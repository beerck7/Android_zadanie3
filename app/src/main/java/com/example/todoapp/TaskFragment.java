package com.example.todoapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";

    private Task mTask;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mDoneCheckBox;

    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID taskId = null;
        Bundle args = getArguments();
        if (args != null) {
            taskId = (UUID) args.getSerializable(ARG_TASK_ID);
        }

        if (taskId != null) {
            mTask = TaskStorage.get(requireContext()).getTask(taskId);
        } else {
            mTask = new Task();
        }

        getParentFragmentManager().setFragmentResultListener(
                DatePickerFragment.REQUEST_KEY,
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Date date = (Date) result.getSerializable(DatePickerFragment.RESULT_DATE);
                        if (date != null) {
                            mTask.setDate(date);
                            updateDateButton();
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_task, container, false);

        mTitleField = v.findViewById(R.id.task_title);
        mDateButton = v.findViewById(R.id.task_date_button);
        mDoneCheckBox = v.findViewById(R.id.task_done);

        mTitleField.setText(mTask.getTitle());
        mDoneCheckBox.setChecked(mTask.isDone());
        updateDateButton();

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        mDoneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> mTask.setDone(isChecked));

        mDateButton.setOnClickListener(v1 -> {
            DatePickerFragment dialog = DatePickerFragment.newInstance(mTask.getDate());
            dialog.show(getParentFragmentManager(), "DialogDate");
        });

        return v;
    }

    private void updateDateButton() {
        String dateStr = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
                .format(mTask.getDate());
        mDateButton.setText(dateStr);
    }
}
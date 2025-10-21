package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskListFragment extends Fragment {

    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskRecyclerView = view.findViewById(R.id.task_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        List<Task> tasks = TaskStorage.get(requireContext()).getTasks();
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mTaskRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mNameTextView;
        private final TextView mDateTextView;
        private Task mTask;

        TaskHolder(@NonNull LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            mNameTextView = itemView.findViewById(R.id.task_item_name);
            mDateTextView = itemView.findViewById(R.id.task_item_date);
        }

        void bind(Task task) {
            mTask = task;
            mNameTextView.setText(task.getTitle());
            String dateStr = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
                    .format(task.getDate());
            mDateTextView.setText(dateStr);
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.newIntent(requireContext(), mTask.getId());
            startActivity(intent);
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TaskHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            holder.bind(mTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTasks != null ? mTasks.size() : 0;
        }
    }
}
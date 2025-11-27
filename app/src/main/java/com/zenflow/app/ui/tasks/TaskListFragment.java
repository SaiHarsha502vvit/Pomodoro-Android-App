package com.zenflow.app.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zenflow.app.R;
import com.zenflow.app.ui.main.MainActivity;

import java.util.ArrayList;

public class TaskListFragment extends Fragment {

    private TaskListViewModel viewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private FloatingActionButton fabAddTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        fabAddTask = view.findViewById(R.id.fabAddTask);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(task -> {
            viewModel.toggleTaskComplete(task);
        });
        recyclerView.setAdapter(adapter);

        TaskListViewModelFactory factory = new TaskListViewModelFactory(
                ((MainActivity) requireActivity()).getRepository()
        );
        viewModel = new ViewModelProvider(this, factory).get(TaskListViewModel.class);

        viewModel.getActiveTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                adapter.setTasks(tasks);
            }
        });

        fabAddTask.setOnClickListener(v -> {
            AddTaskBottomSheet bottomSheet = new AddTaskBottomSheet();
            bottomSheet.show(getParentFragmentManager(), "AddTaskBottomSheet");
        });

        return view;
    }
}


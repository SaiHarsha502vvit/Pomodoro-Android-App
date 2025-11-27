package com.zenflow.app.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zenflow.app.R;
import com.zenflow.app.ui.main.MainActivity;

public class AddTaskBottomSheet extends BottomSheetDialogFragment {

    private EditText etTitle;
    private EditText etDescription;
    private Spinner spinnerCategory;
    private Spinner spinnerPriority;
    private EditText etPomodoros;
    private Button btnAdd;
    private TaskListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_task, container, false);

        etTitle = view.findViewById(R.id.etTaskTitle);
        etDescription = view.findViewById(R.id.etTaskDescription);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerPriority = view.findViewById(R.id.spinnerPriority);
        etPomodoros = view.findViewById(R.id.etEstimatedPomodoros);
        btnAdd = view.findViewById(R.id.btnAddTask);

        // Setup ViewModel
        TaskListViewModelFactory factory = new TaskListViewModelFactory(
                ((MainActivity) requireActivity()).getRepository()
        );
        viewModel = new ViewModelProvider(requireParentFragment(), factory).get(TaskListViewModel.class);

        // Setup category spinner
        String[] categories = {"Work", "Study", "Personal", "Exercise", "Other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Setup priority spinner
        String[] priorities = {"Low", "Medium", "High"};
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                priorities
        );
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);
        spinnerPriority.setSelection(1); // Default to Medium

        // Add task button
        btnAdd.setOnClickListener(v -> addTask());

        return view;
    }

    private void addTask() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        int priority = spinnerPriority.getSelectedItemPosition() + 1; // 1-3

        int pomodoros = 0;
        String pomodorosStr = etPomodoros.getText().toString().trim();
        if (!pomodorosStr.isEmpty()) {
            try {
                pomodoros = Integer.parseInt(pomodorosStr);
            } catch (NumberFormatException e) {
                pomodoros = 0;
            }
        }

        if (title.isEmpty()) {
            etTitle.setError("Title is required");
            return;
        }

        viewModel.addTask(title, description, category, priority, pomodoros);
        dismiss();
    }
}

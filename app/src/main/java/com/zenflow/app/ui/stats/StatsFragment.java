package com.zenflow.app.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.R;
import com.zenflow.app.ui.main.MainActivity;

public class StatsFragment extends Fragment {

    private StatsViewModel viewModel;
    private TextView tvTotalSessions;
    private TextView tvTotalMinutes;
    private TextView tvTotalTasks;
    private TextView tvTotalHours;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        tvTotalSessions = view.findViewById(R.id.tvTotalSessions);
        tvTotalMinutes = view.findViewById(R.id.tvTotalMinutes);
        tvTotalTasks = view.findViewById(R.id.tvCompletedTasks);
        tvTotalHours = view.findViewById(R.id.tvTotalHours);

        // Setup ViewModel
        StatsViewModelFactory factory = new StatsViewModelFactory(
                ((MainActivity) requireActivity()).getRepository()
        );
        viewModel = new ViewModelProvider(this, factory).get(StatsViewModel.class);

        // Observe statistics
        viewModel.getTotalFocusSessions().observe(getViewLifecycleOwner(), sessions -> {
            if (sessions != null) {
                tvTotalSessions.setText(String.valueOf(sessions));
            }
        });

        viewModel.getTotalFocusMinutes().observe(getViewLifecycleOwner(), minutes -> {
            if (minutes != null) {
                tvTotalMinutes.setText(minutes + " min");
                double hours = minutes / 60.0;
                tvTotalHours.setText(String.format("%.1f hours", hours));
            }
        });

        viewModel.getCompletedTaskCount().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                tvTotalTasks.setText(String.valueOf(tasks));
            }
        });

        return view;
    }
}

package com.zenflow.app.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zenflow.app.R;
import com.zenflow.app.ui.main.MainActivity;

public class AchievementsFragment extends Fragment {

    private AchievementsViewModel viewModel;
    private RecyclerView recyclerView;
    private AchievementAdapter adapter;
    private TextView tvUnlockedCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAchievements);
        tvUnlockedCount = view.findViewById(R.id.tvUnlockedCount);

        // Setup RecyclerView with grid layout
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new AchievementAdapter();
        recyclerView.setAdapter(adapter);

        // Setup ViewModel
        AchievementsViewModelFactory factory = new AchievementsViewModelFactory(
                ((MainActivity) requireActivity()).getRepository()
        );
        viewModel = new ViewModelProvider(this, factory).get(AchievementsViewModel.class);

        // Observe achievements
        viewModel.getAllAchievements().observe(getViewLifecycleOwner(), achievements -> {
            adapter.setAchievements(achievements);
        });

        viewModel.getUnlockedCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null) {
                tvUnlockedCount.setText("Unlocked: " + count);
            }
        });

        return view;
    }
}

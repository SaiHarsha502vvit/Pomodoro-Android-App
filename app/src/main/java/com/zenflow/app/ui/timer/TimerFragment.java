package com.zenflow.app.ui.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.R;
import com.zenflow.app.timer.TimerState;
import com.zenflow.app.ui.main.MainActivity;

public class TimerFragment extends Fragment {

    private TimerViewModel viewModel;
    private TextView tvCycle, tvPhase, tvTimer;
    private Button btnStartFocus, btnPause, btnStop, btnShortBreak, btnLongBreak;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        tvCycle = view.findViewById(R.id.tvCycle);
        tvPhase = view.findViewById(R.id.tvPhase);
        tvTimer = view.findViewById(R.id.tvTimer);
        btnStartFocus = view.findViewById(R.id.btnStartFocus);
        btnPause = view.findViewById(R.id.btnPause);
        btnStop = view.findViewById(R.id.btnStop);
        btnShortBreak = view.findViewById(R.id.btnShortBreak);
        btnLongBreak = view.findViewById(R.id.btnLongBreak);

        TimerViewModelFactory factory = new TimerViewModelFactory(
                requireActivity().getApplication(),
                ((MainActivity) requireActivity()).getRepository()
        );
        viewModel = new ViewModelProvider(requireActivity(), factory).get(TimerViewModel.class);

        setupObservers();
        setupClickListeners();

        return view;
    }

    private void setupObservers() {
        viewModel.getTimerState().observe(getViewLifecycleOwner(), state -> {
            if (state != null) {
                updateUI(state);
            }
        });
    }

    private void setupClickListeners() {
        btnStartFocus.setOnClickListener(v -> viewModel.startFocus());
        btnPause.setOnClickListener(v -> viewModel.pauseResume());
        btnStop.setOnClickListener(v -> viewModel.stop());
        btnShortBreak.setOnClickListener(v -> viewModel.startShortBreak());
        btnLongBreak.setOnClickListener(v -> viewModel.startLongBreak());
    }

    private void updateUI(TimerState state) {
        tvCycle.setText("Cycle: " + state.cycleNumber);
        tvTimer.setText(state.getFormattedTime());

        switch (state.phase) {
            case IDLE:
                tvPhase.setText("Ready");
                break;
            case FOCUS:
                tvPhase.setText("Focus Time");
                break;
            case SHORT_BREAK:
                tvPhase.setText("Short Break");
                break;
            case LONG_BREAK:
                tvPhase.setText("Long Break");
                break;
            default:
                tvPhase.setText("Ready");
                break;
        }

        boolean isRunning = state.running;
        btnStartFocus.setEnabled(!isRunning);
        btnPause.setEnabled(isRunning);
        btnStop.setEnabled(isRunning);
        btnShortBreak.setEnabled(!isRunning);
        btnLongBreak.setEnabled(!isRunning);

        btnPause.setText(state.paused ? "Resume" : "Pause");
    }
}

package com.zenflow.app.ui.mood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zenflow.app.R;
import com.zenflow.app.ui.main.MainActivity;

public class MoodCheckInFragment extends BottomSheetDialogFragment {

    private Spinner spinnerMoodBefore;
    private Spinner spinnerMoodAfter;
    private EditText etNote;
    private Button btnSubmit;
    private MoodViewModel viewModel;
    private int sessionId;

    public static MoodCheckInFragment newInstance(int sessionId) {
        MoodCheckInFragment fragment = new MoodCheckInFragment();
        Bundle args = new Bundle();
        args.putInt("session_id", sessionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_check_in, container, false);

        spinnerMoodBefore = view.findViewById(R.id.spinnerMoodBefore);
        spinnerMoodAfter = view.findViewById(R.id.spinnerMoodAfter);
        etNote = view.findViewById(R.id.etMoodNote);
        btnSubmit = view.findViewById(R.id.btnSubmitMood);

        if (getArguments() != null) {
            sessionId = getArguments().getInt("session_id", -1);
        }

        // Setup ViewModel
        MoodViewModelFactory factory = new MoodViewModelFactory(
                ((MainActivity) requireActivity()).getRepository()
        );
        viewModel = new ViewModelProvider(this, factory).get(MoodViewModel.class);

        // Setup mood spinners
        String[] moods = {"Calm", "Anxious", "Energetic", "Tired", "Happy", "Stressed", "Focused", "Distracted"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                moods
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMoodBefore.setAdapter(adapter);
        spinnerMoodAfter.setAdapter(adapter);

        btnSubmit.setOnClickListener(v -> submitMood());

        return view;
    }

    private void submitMood() {
        String moodBefore = spinnerMoodBefore.getSelectedItem().toString();
        String moodAfter = spinnerMoodAfter.getSelectedItem().toString();
        String note = etNote.getText().toString().trim();

        viewModel.logMood(sessionId, moodBefore, moodAfter, note);

        Toast.makeText(getContext(), "Mood logged successfully!", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}

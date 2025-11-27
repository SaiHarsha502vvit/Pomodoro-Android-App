package com.zenflow.app.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zenflow.app.R;
import com.zenflow.app.ZenFlowApp;
import com.zenflow.app.repository.ZenFlowRepository;
import com.zenflow.app.ui.achievements.AchievementsFragment;
import com.zenflow.app.ui.stats.StatsFragment;
import com.zenflow.app.ui.tasks.TaskListFragment;
import com.zenflow.app.ui.timer.TimerFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MainViewModel viewModel;
    private ZenFlowRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get repository from application
        repository = ((ZenFlowApp) getApplication()).getRepository();

        // Initialize ViewModel
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        // Setup bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new TimerFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_timer);
        }
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_tasks) {
            fragment = new TaskListFragment();
        } else if (itemId == R.id.nav_timer) {
            fragment = new TimerFragment();
        } else if (itemId == R.id.nav_stats) {
            fragment = new StatsFragment();
        } else if (itemId == R.id.nav_achievements) {
            fragment = new AchievementsFragment();
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public ZenFlowRepository getRepository() {
        return repository;
    }
}

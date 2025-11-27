package com.zenflow.app.ui.achievements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zenflow.app.R;
import com.zenflow.app.data.local.entities.AchievementEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private List<AchievementEntity> achievements = new ArrayList<>();

    public void setAchievements(List<AchievementEntity> achievements) {
        this.achievements = achievements != null ? achievements : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        AchievementEntity achievement = achievements.get(position);
        holder.bind(achievement);
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvUnlockedDate;
        private View lockOverlay;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivAchievementIcon);
            tvTitle = itemView.findViewById(R.id.tvAchievementTitle);
            tvDescription = itemView.findViewById(R.id.tvAchievementDescription);
            tvUnlockedDate = itemView.findViewById(R.id.tvUnlockedDate);
            lockOverlay = itemView.findViewById(R.id.lockOverlay);
        }

        public void bind(AchievementEntity achievement) {
            tvTitle.setText(achievement.title);
            tvDescription.setText(achievement.description);

            if (achievement.unlocked) {
                lockOverlay.setVisibility(View.GONE);
                itemView.setAlpha(1.0f);

                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                String dateText = "Unlocked: " + sdf.format(new Date(achievement.unlockedAt));
                tvUnlockedDate.setText(dateText);
                tvUnlockedDate.setVisibility(View.VISIBLE);
            } else {
                lockOverlay.setVisibility(View.VISIBLE);
                itemView.setAlpha(0.5f);
                tvUnlockedDate.setVisibility(View.GONE);
            }

            // Set icon based on achievement key (you can expand this)
            ivIcon.setImageResource(R.drawable.ic_achievement);
        }
    }
}

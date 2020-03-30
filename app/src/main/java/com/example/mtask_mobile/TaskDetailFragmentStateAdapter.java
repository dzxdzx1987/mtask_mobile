package com.example.mtask_mobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mtask_mobile.TaskDetailFragment;

import java.util.List;

public class TaskDetailFragmentStateAdapter extends FragmentStateAdapter {

    private List<Integer> mColors;

    public TaskDetailFragmentStateAdapter(@NonNull FragmentManager fragmentManager, List<Integer> colors) {
        super(fragmentManager);
        mColors = colors;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return TaskDetailFragment.newInstance(mColors, position);
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }
}

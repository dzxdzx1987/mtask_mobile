package com.example.mtask_mobile.board.view;

import android.content.Intent;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;

import com.example.mtask_mobile.BaseActivity;
import com.example.mtask_mobile.LoginActivity;
import com.example.mtask_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

public class BoardActivity extends BaseActivity {
    BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    private BoardInfoFragment boardInfoFragment = new BoardInfoFragment();
    private BoardTaskListFragment boardTaskListFragment = new BoardTaskListFragment();
    private BoardProgressFragment boardProgressFragment = new BoardProgressFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_board_info:
                    transaction.replace(R.id.frame_layout, boardInfoFragment).commit();
                    return true;
                case R.id.navigation_board_task_list:
                    transaction.replace(R.id.frame_layout, boardTaskListFragment).commit();
                    return true;
                case R.id.navigation_board_progress:
                    transaction.replace(R.id.frame_layout, boardProgressFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.navigation_board);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, boardTaskListFragment).commitAllowingStateLoss();
    }

    @Override
    public void onNetworkStateChanged(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

package com.example.mtask_mobile;

import android.content.Intent;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
                    transaction.replace(R.id.frame_layout, boardInfoFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_board_task_list:
                    transaction.replace(R.id.frame_layout, boardTaskListFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_board_progress:
                    transaction.replace(R.id.frame_layout, boardProgressFragment).commitAllowingStateLoss();
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
        transaction.replace(R.id.frame_layout, boardTaskListFragment).commitAllowingStateLoss();
    }

    @Override
    public void onNetworkStateChanged(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

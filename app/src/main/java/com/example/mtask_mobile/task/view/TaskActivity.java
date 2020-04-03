package com.example.mtask_mobile.task.view;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.mtask_mobile.BaseActivity;
import com.example.mtask_mobile.LoginActivity;
import com.example.mtask_mobile.R;
import com.example.mtask_mobile.task.adapter.TaskDetailFragmentStateAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.vo.BranchGroupInfo;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends BaseActivity {
    private static final String TAG = TaskActivity.class.getSimpleName();

    public static final String TASK_NAME = "task_name";
    public static final String TASK_CONTENT = "task_desc";

    public static final String TASK_OWNER_IMAGE_URL = "task_owner_image_url";

    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private List<Integer> mColors = new ArrayList<>();
    private TaskDetailFragmentStateAdapter mAdapter;

    {
        mColors.add(android.R.color.black);
        mColors.add(android.R.color.holo_purple);
        mColors.add(android.R.color.holo_blue_dark);
        mColors.add(android.R.color.holo_green_light);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        String taskName = intent.getStringExtra(TASK_NAME);
        String taskContent = intent.getStringExtra(TASK_CONTENT);
        String taskOwnerImageUrl = intent.getStringExtra(TASK_OWNER_IMAGE_URL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        ImageView taskOwnerImageView = findViewById(R.id.task_owner_image_view);
        //TextView taskContentText = findViewById(R.id.task_content_text);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(taskName);

        Glide.with(this).load(taskOwnerImageUrl).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(taskOwnerImageView);
        // String taskContent = generateTaskContent(taskName);
        //taskContentText.setText(taskContent);

        List<BranchGroupInfo> branchGroupInfoList = LitePal.findAll(BranchGroupInfo.class);
        LogUtil.d(TAG, branchGroupInfoList.toString());

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager2 = findViewById(R.id.viewpager2);
        mAdapter = new TaskDetailFragmentStateAdapter(getSupportFragmentManager(), mColors);
        mViewPager2.setAdapter(mAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab0"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab3"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        // 注册页面变化的回调接口
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.setScrollPosition(position,0,false);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetworkStateChanged(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

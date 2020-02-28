package com.example.mtask_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.vo.BranchGroupInfo;

import org.litepal.LitePal;

import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private static final String TAG = TaskActivity.class.getSimpleName();

    public static final String TASK_NAME = "task_name";

    public static final String TASK_OWNER_IMAGE_URL = "task_owner_image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        String taskName = intent.getStringExtra(TASK_NAME);
        String taskOwnerImageUrl = intent.getStringExtra(TASK_OWNER_IMAGE_URL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        ImageView taskOwnerImageView = findViewById(R.id.task_owner_image_view);
        TextView taskContentText = findViewById(R.id.task_content_text);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(taskName);

        Glide.with(this).load(taskOwnerImageUrl).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(taskOwnerImageView);
        String taskContent = generateTaskContent(taskName);
        taskContentText.setText(taskContent);

        List<BranchGroupInfo> branchGroupInfoList = LitePal.findAll(BranchGroupInfo.class);
        LogUtil.d(TAG, branchGroupInfoList.toString());

    }

    private String generateTaskContent (String taskName) {
        StringBuilder taskContent = new StringBuilder();
        for (int i = 0; i < 500; i ++) {
            taskContent.append(taskName);
        }
        return taskContent.toString();
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
}

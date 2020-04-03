package com.example.mtask_mobile;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = BaseActivity.class.getSimpleName();
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        List<BaseActivity> activityList = MTaskApplication.getActivityList();
        if (!activityList.contains(this)) {
            activityList.add(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        List<BaseActivity> activityList = MTaskApplication.getActivityList();
        if (activityList.contains(this)) {
            activityList.remove(this);
        }
    }

    abstract public void onNetworkStateChanged(NetworkInfo networkInfo);
}

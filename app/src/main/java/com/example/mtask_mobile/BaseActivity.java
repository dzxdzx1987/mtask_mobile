package com.example.mtask_mobile;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mtask_mobile.livedata.NetworkLiveData;

import java.util.ArrayList;
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

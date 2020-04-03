package com.example.mtask_mobile;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.example.mtask_mobile.receiver.ConnectivityManagerReceiver;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

public class MTaskApplication extends LitePalApplication {

    private final static String TAG = MTaskApplication.class.getSimpleName();
    private static Context mContext;
    private ConnectivityManagerReceiver mNetworkReceiver;
    private IntentFilter mIntentFilter;

    private static List<BaseActivity> mActivityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        // Utility.requestBranchList();
        LitePal.getDatabase();
        // LitePal.deleteAll(BranchGroupInfo.class);
        mNetworkReceiver = new ConnectivityManagerReceiver();
        mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, mIntentFilter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mNetworkReceiver);
    }

    public static Context getContext () {
        return mContext;
    }

    public static List<BaseActivity> getActivityList() {
        return mActivityList;
    }
}

package com.example.mtask_mobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.mtask_mobile.BaseActivity;
import com.example.mtask_mobile.MTaskApplication;

public class ConnectivityManagerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = mManager.getActiveNetworkInfo();

        for (BaseActivity baseActivity : MTaskApplication.getActivityList()) {
            baseActivity.onNetworkStateChanged(activeNetwork);
        }

    }
}

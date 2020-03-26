package com.example.mtask_mobile;

import android.content.Intent;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mtask_mobile.service.AutoUpdateService;

public class ChooseBranchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_branch);
        //Intent intent = new Intent(this, AutoUpdateService.class);
        //startService(intent);
    }

    @Override
    public void onNetworkStateChanged(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

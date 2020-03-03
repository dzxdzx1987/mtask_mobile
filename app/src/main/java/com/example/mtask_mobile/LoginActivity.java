package com.example.mtask_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtask_mobile.vo.BranchGroupInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = LoginActivity.class.getSimpleName();

    private Button mSelectBranchBtn;
    private Button mLoginBtn;
    private TextView mSelectedBranchText;
    private EditText mLoginUserIdText;
    private EditText mLoginPassText;

    private final int MESSAGE_EXIT = 1;
    public final static int SELECT_BRANCH_RESULT = 10;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_EXIT:
                    finish();
                    break;
                default:
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!isNetworkConnected()) {
            Toast.makeText(this, "Network is unavailable.", Toast.LENGTH_LONG).show();
            Message message = new Message();
            message.what = MESSAGE_EXIT;
            mHandler.sendMessageDelayed(message, 3000);
        }

        initView();
    }

    private void initView() {
        mSelectBranchBtn = findViewById(R.id.select_branch_btn);
        mSelectBranchBtn.setOnClickListener(this);
        mSelectedBranchText = findViewById(R.id.selected_branch_text);
        mLoginUserIdText = findViewById(R.id.login_id_text);
        mLoginPassText = findViewById(R.id.login_pass_text);
        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_branch_btn:
                Intent intent = new Intent(this, ChooseBranchActivity.class);
                startActivityForResult(intent, SELECT_BRANCH_RESULT);
                break;
            case R.id.login_btn:
                // login process
                String id = mLoginUserIdText.getText().toString();
                String pass = mLoginPassText.getText().toString();
                if (id.equals("test") && pass.equals("123")) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    prefs.edit().putString("userId", id).apply();

                    Intent in = new Intent(this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_BRANCH_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                final BranchGroupInfo branchGroupInfo = data.getParcelableExtra("branchGroupInfo");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSelectedBranchText.setText(branchGroupInfo.getName());
                    }
                });

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

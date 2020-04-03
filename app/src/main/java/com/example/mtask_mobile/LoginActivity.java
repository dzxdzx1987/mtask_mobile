package com.example.mtask_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mtask_mobile.repository.UserRepository;
import com.example.mtask_mobile.vo.BranchGroupInfo;
import com.example.mtask_mobile.vo.LoginUserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = LoginActivity.class.getSimpleName();

    private Context mContext;
    private Button mSelectBranchBtn;
    private Button mLoginBtn;
    private TextView mSelectedBranchText;
    private EditText mLoginUserIdText;
    private EditText mLoginPassText;
    private ProgressDialog progressDialog;

    private final int MESSAGE_NETWORK_DISABLE = 1;
    private final int MESSAGE_NETWORK_ENABLE = 2;
    public final static int SELECT_BRANCH_RESULT = 10;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NETWORK_DISABLE:
                    mLoginBtn.setEnabled(false);
                    //finish();
                    break;
                case MESSAGE_NETWORK_ENABLE:
                    mLoginBtn.setEnabled(true);
                default:
            }
            super.handleMessage(msg);
        }
    };

    private UserRepository.IUserCallback userCallback = new UserRepository.IUserCallback(){
        @Override
        public void onSuccess(JSONObject object) {
            JSONObject userInfo = object;
            try {
                String id = userInfo.getString("id");
                String email = userInfo.getString("email");
                String name = userInfo.getString("name");
                String companyId = userInfo.getString("companyId");
                String branchId = userInfo.getString("branchId");

                LitePal.deleteAll(LoginUserInfo.class);

                LoginUserInfo user = new LoginUserInfo();
                user.setUuid(id);
                user.setEmail(email);
                user.setName(name);
                user.setPassword(mLoginPassText.getText().toString());
                user.setCompanyId(companyId);
                user.setBranchId(branchId);
                user.save();

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    closeProgressDialog();
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
            });
        }

        @Override
        public void onFailure(int errorCode) {
            switch (errorCode) {
                case 0:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            Toast.makeText(mContext, "your password is not correct", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                default:

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
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

        if (!isNetworkConnected()) {
            Toast.makeText(this, "Network is unavailable.", Toast.LENGTH_LONG).show();
            mLoginBtn.setEnabled(false);
        } else {
            mLoginBtn.setEnabled(true);
        }
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
                showProgressDialog();
                String id = mLoginUserIdText.getText().toString();
                String pass = mLoginPassText.getText().toString();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String branchId = prefs.getString("branchId", null);

                LoginUserInfo userInfo = new LoginUserInfo();
                userInfo.setBranchId(branchId);
                userInfo.setEmail(id);
                userInfo.setPassword(pass);
                UserRepository.getInstance().userLogin(userInfo, userCallback);
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

    @Override
    public void onNetworkStateChanged(NetworkInfo networkInfo) {
        Message message = new Message();
        if (networkInfo == null) {
            Toast.makeText(this, "Network is unavailable.", Toast.LENGTH_LONG).show();
            message.what = MESSAGE_NETWORK_DISABLE;
        } else {
            message.what = MESSAGE_NETWORK_ENABLE;
        }
        mHandler.sendMessageDelayed(message, 3000);
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

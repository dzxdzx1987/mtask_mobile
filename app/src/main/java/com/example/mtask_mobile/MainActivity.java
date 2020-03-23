package com.example.mtask_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.com.example.mtask_mobile.vo.Task;
import com.example.mtask_mobile.repository.UserRepository;
import com.example.mtask_mobile.util.HttpUtil;
import com.example.mtask_mobile.util.Utility;
import com.example.mtask_mobile.vo.BranchGroupInfo;
import com.example.mtask_mobile.vo.BranchUserInfo;
import com.example.mtask_mobile.vo.LoginUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;

    private SwipeRefreshLayout swipeRefresh;

    private TextView mUserName;

    private TextView mUserEmail;

    private Context mContext;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private UserRepository.IUserCallback userCallback = new UserRepository.IUserCallback(){
        @Override
        public void onSuccess(JSONObject object) {

        }

        @Override
        public void onFailure(int errorCode) {
            switch (errorCode) {
                case 0:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "your login info is not correct", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                            finish();
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
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        LoginUserInfo userInfo = LitePal.findFirst(LoginUserInfo.class);

        UserRepository.getInstance().userLogin(userInfo, userCallback);

        if (userInfo == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            /*mUserName = findViewById(R.id.user_name);
            mUserEmail = findViewById(R.id.user_email);

            String name = userInfo.getName();
            String email = userInfo.getEmail();

            if (name != null && email != null) {
                mUserName.setText(name);
                mUserEmail.setText(email);
            }*/

            initView();
        }
    }

    private void initView() {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_list_view, new MyTaskFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTasks();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView naviView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        naviView.setCheckedItem(R.id.my_task_menu);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.my_task_menu:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_list_view, new MyTaskFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.my_board_menu:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_list_view, new BoardListFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.favourite_board_menu:
                        break;
                    case R.id.trash_menu:
                        break;
                    case R.id.my_info_menu:
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();

                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                prefs.edit().putString("userId", null).apply();
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void refreshTasks () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_list_view, new MyTaskFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}

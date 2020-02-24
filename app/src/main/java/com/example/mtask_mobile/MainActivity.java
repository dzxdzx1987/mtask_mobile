package com.example.mtask_mobile;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mtask_mobile.com.example.mtask_mobile.vo.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Task[] tasks = {
            new Task("Task A", "http://wx2.sinaimg.cn/large/006m97Kgly1ftmb1yc189j30v90usq66.jpg", "content about A"),
            new Task("Task B", "https://twgreatdaily.com/images/elastic/Cg4/Cg4s6mwBJleJMoPMGj46.jpg", "content about B"),
            new Task("Task C", "https://twgreatdaily.com/images/elastic/Sw4/Sw4sgl6mwBJleJMoPMJD6I.jpg", "content about C"),
            new Task("Task D", "https://twgreatdaily.com/images/elastic/sw4/sw4s6mwBJleJMoPMMz7c.jpg", "content about D"),
            new Task("Task E", "https://twgreatdaily.com/images/elastic/vw4/vw4s6mwBJleJMoPMNT4i.jpg", "content about E"),
            new Task("Task F", "https://twgreatdaily.com/images/elastic/VA4/VA4s6mwBJleJMoPMJT62.jpg", "content about F"),
            new Task("Task G", "https://twgreatdaily.com/images/elastic/Qw4/Qw4s6mwBJleJMoPMIz4w.jpg", "content about G"),
            new Task("Task H", "https://twgreatdaily.com/images/elastic/Wkh/WkhVxW8BjYh_GJGVMyd8.jpg", "content about H"),
            new Task("Task I", "https://twgreatdaily.com/images/elastic/ZUh/ZUhVxW8BjYh_GJGVOydT.jpg", "content about I"),
            new Task("Task J", "https://twgreatdaily.com/images/elastic/bUh/bUhVxW8BjYh_GJGVQieB.jpg", "content about J"),
    };

    private List<Task> taskList = new ArrayList<>();

    private TaskAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTasks();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);

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
        naviView.setCheckedItem(R.id.nav_call);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
                Toast.makeText(this, "You clicked More", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void initTasks () {
        taskList.clear();
        for (int i = 0; i < 50; i ++) {
            Random random = new Random();
            int index = random.nextInt(tasks.length);
            taskList.add(tasks[index]);
        }
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
                        initTasks();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}

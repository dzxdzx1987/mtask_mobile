package com.example.mtask_mobile.task.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mtask_mobile.R;

public class NewTaskActivity extends AppCompatActivity {

    private Button mNewTaskBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        mNewTaskBtn = findViewById(R.id.new_task_btn);
        mNewTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });
    }

    private void showNormalDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(NewTaskActivity.this);
        dialog.setIcon(R.drawable.logo)
                .setTitle("Normal Dialog")
                .setMessage("Click button")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
    }
}

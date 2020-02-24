package com.example.mtask_mobile;

import android.app.Application;
import android.content.Context;

public class MTaskApplicatiom extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext () {
        return context;
    }
}

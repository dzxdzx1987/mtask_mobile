package com.example.mtask_mobile;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.service.AutoUpdateService;
import com.example.mtask_mobile.util.HttpUtil;
import com.example.mtask_mobile.util.Utility;
import com.example.mtask_mobile.vo.BranchGroupInfo;
import com.example.mtask_mobile.vo.BranchUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.HashMap;
import java.util.Map;

public class MTaskApplication extends LitePalApplication {

    private final static String TAG = MTaskApplication.class.getSimpleName();
    private static Context context;
    public final static String COOKIE = "_ga=GA1.3.438162699.1556609940; connect.sid=s%3AJ9oXXSTJPCUKyQyMKJZEEMl_s0fPTLDF.iEVytAkWNrLZ%2BhMK9iS9E8FDond0FdNbQKBJOVK5plg; _gid=GA1.3.1781206052.1583136568; _gat_gtag_UA_131151268_1=1";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // Utility.requestBranchList();
        LitePal.getDatabase();
        // LitePal.deleteAll(BranchGroupInfo.class);
    }

    public static Context getContext () {
        return context;
    }
}

package com.example.mtask_mobile;

import android.app.Application;
import android.content.Context;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MTaskApplicatiom extends Application {

    private final static String TAG = MTaskApplicatiom.class.getSimpleName();
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        requestBranchList();
    }

    public static Context getContext () {
        return context;
    }

    private void requestBranchList () {
        RequestQueue queue = null;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();

        String branchUrl = "https://mtask.motrex.co.kr/branches";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, branchUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean result = response.getBoolean("result");
                            if (result) {
                                JSONArray branchList =  response.getJSONArray("data");
                                LogUtil.d(TAG, branchList.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e(TAG, error.toString());
            }
        });
        queue.add(request);
    }
}

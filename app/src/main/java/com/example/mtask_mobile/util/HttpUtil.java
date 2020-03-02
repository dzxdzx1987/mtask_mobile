package com.example.mtask_mobile.util;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mtask_mobile.MTaskApplication;

import org.json.JSONObject;

public class HttpUtil {

    private static HttpUtil httpUtil;

    private RequestQueue queue = null;


    private HttpUtil() {
        init();
    }

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            return new HttpUtil();
        } else {
            return  httpUtil;
        }
    }

    private void init() {
        Cache cache = new DiskBasedCache(MTaskApplication.getContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();
    }

    public void makeJsonObjectRequest(String url, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, successListener, errorListener);
        queue.add(request);
    }
}

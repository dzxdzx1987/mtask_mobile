package com.example.mtask_mobile.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mtask_mobile.MTaskApplication;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.android.volley.VolleyLog.TAG;

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

    public void makeJsonObjectRequestWithHeaders(String url, final Map<String, String> headers, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, successListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = headers;
                return params;
            }
        };
        queue.add(request);
    }

    public void makeRequest(String url, int method, final Map<String, String> headers, final Map<String, String> body, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = new JSONObject(body);
        JsonObjectRequest request = new JsonObjectRequest(method, url, jsonObject , successListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = headers;
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                String cookie = response.headers.get("set-cookie");
                saveCookie(cookie);
                LogUtil.d(TAG, cookie);
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }

    private void saveCookie(String cookie) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        String[] arr = cookie.split(";");
        for (String str : arr) {
            if (set.contains(str)) {
                continue;
            }
            set.add(str);
        }

        for (String c : set) {
            sb.append(c).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MTaskApplication.getContext());
        prefs.edit().putString("cookie", sb.toString()).apply();
    }

}

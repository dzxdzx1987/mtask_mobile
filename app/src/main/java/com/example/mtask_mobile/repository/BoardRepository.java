package com.example.mtask_mobile.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mtask_mobile.MTaskApplication;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BoardRepository {
    private final static String TAG = BoardRepository.class.getSimpleName();
    public static final int PASS_ERROR = 0;

    public interface IBoardCallback {
        void onSuccess(JSONObject object);
        void onFailure(int errorCode);
    }

    private static BoardRepository instance;

    private Context context;

    private BoardRepository() {
        context = MTaskApplication.getContext();
    }

    public static BoardRepository getInstance() {
        if (instance == null) {
            instance = new BoardRepository();
            return instance;
        } else {
            return  instance;
        }
    }


    public void requestBoardList(final IBoardCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MTaskApplication.getContext());
        String cookie = prefs.getString("cookie", null);
        headers.put("Cookie", cookie);
        HttpUtil.getInstance().makeJsonObjectRequestWithHeaders("https://mtask.motrex.co.kr/boards", headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d(TAG, response.toString());
                        try {
                            boolean res = response.getBoolean("result");
                            if (res) {
                                callback.onSuccess(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(PASS_ERROR);
                        LogUtil.e(TAG, error.getMessage());
                    }
                });
    }

    public void requestBoardStatusList(String boardId, final IBoardCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MTaskApplication.getContext());
        String cookie = prefs.getString("cookie", null);
        headers.put("Cookie", cookie);
        HttpUtil.getInstance().makeJsonObjectRequestWithHeaders("https://mtask.motrex.co.kr/boards/" + boardId + "/stats", headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d(TAG, response.toString());
                        try {
                            boolean res = response.getBoolean("result");
                            if (res) {
                                callback.onSuccess(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(PASS_ERROR);
                        LogUtil.e(TAG, error.getMessage());
                    }
                });
    }
}

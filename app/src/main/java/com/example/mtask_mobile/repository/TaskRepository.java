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

public class TaskRepository {

    private final static String TAG = TaskRepository.class.getSimpleName();
    public static final int PASS_ERROR = 0;

    public interface ITaskCallback {
        void onSuccess(JSONObject object);
        void onFailure(int errorCode);
    }

    private static TaskRepository instance;

    private Context mContext;

    private TaskRepository() {
        mContext = MTaskApplication.getContext();
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
            return instance;
        } else {
            return  instance;
        }
    }

    public void requestTaskListByBoardId(String boardId, final ITaskCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MTaskApplication.getContext());
        String cookie = prefs.getString("cookie", null);
        headers.put("Cookie", cookie);
        HttpUtil.getInstance().makeJsonObjectRequestWithHeaders("https://mtask.motrex.co.kr/boards/" + boardId + "/tasks", headers,
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

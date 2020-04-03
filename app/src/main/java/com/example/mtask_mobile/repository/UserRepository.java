package com.example.mtask_mobile.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mtask_mobile.MTaskApplication;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.util.HttpUtil;
import com.example.mtask_mobile.vo.LoginUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final static String TAG = UserRepository.class.getSimpleName();

    public static final int PASS_ERROR = 0;

    public interface IUserCallback {
        void onSuccess(JSONObject object);
        void onFailure(int errorCode);
    }

    private static UserRepository instance;

    private Context context;

    private UserRepository() {
        context = MTaskApplication.getContext();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
            return instance;
        } else {
            return  instance;
        }
    }

    public void userLogin(LoginUserInfo user, final IUserCallback callback) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String branchId = prefs.getString("branchId", null);

        String loginUrl = "https://mtask.motrex.co.kr/login";

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        Map<String, String> body = new HashMap<>();
        body.put("id", branchId);
        body.put("email", user.getEmail());
        body.put("password", user.getPassword());
        HttpUtil.getInstance().makeRequest(loginUrl, Request.Method.POST, header, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean result = response.getBoolean("result");
                    if (result) {
                        callback.onSuccess(response.getJSONObject("data"));
                        JSONObject userInfo = response.getJSONObject("data");
                        LogUtil.d(TAG, userInfo.toString());

                    } else {
                        callback.onFailure(PASS_ERROR);
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
    }

    public void requestBranchUserListByBoardId(String boardId, final IUserCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MTaskApplication.getContext());
        String cookie = prefs.getString("cookie", null);
        headers.put("Cookie", cookie);
        HttpUtil.getInstance().makeJsonObjectRequestWithHeaders("https://mtask.motrex.co.kr/boards/" + boardId + "/users", headers,
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

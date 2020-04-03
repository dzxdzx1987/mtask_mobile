package com.example.mtask_mobile.util;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.vo.BranchGroupInfo;
import com.example.mtask_mobile.vo.BranchUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    private final static String TAG = Utility.class.getSimpleName();

    public static boolean handleBranchGroupList(JSONObject response) {
        boolean result = false;
        try {
            boolean res = response.getBoolean("result");
            if (res) {
                JSONArray branchList =  response.getJSONArray("data");
                LogUtil.d(TAG, branchList.toString());
                for (int i = 0; i < branchList.length(); i ++) {
                    JSONObject object = branchList.getJSONObject(i);
                    BranchGroupInfo branchGroupInfo = new BranchGroupInfo();
                    branchGroupInfo.setBranchId(object.getString("id"));
                    branchGroupInfo.setOrderNo(object.getString("orderNo"));
                    branchGroupInfo.setName(object.getString("name"));
                    branchGroupInfo.save();
                }
                result = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean handleBranchUserInfo(JSONObject response) {
        boolean result = false;
        try {
            boolean res = response.getBoolean("result");
            if (res) {
                JSONObject userInfo =  response.getJSONObject("data");
                if (userInfo != null) {
                    BranchUserInfo branchUserInfo = new BranchUserInfo();
                    branchUserInfo.setBranchId(userInfo.getString("branchId"));
                    branchUserInfo.setUuid(userInfo.getString("id"));
                    branchUserInfo.setName(userInfo.getString("name"));
                    branchUserInfo.setEmail(userInfo.getString("email"));
                    branchUserInfo.save();

                    result = true;
                }
                LogUtil.d(TAG, userInfo.toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
        return result;
    }

    public static void requestBranchList () {
        /*List<BranchGroupInfo> branchGroupInfoList = LitePal.findAll(BranchGroupInfo.class);
        if (branchGroupInfoList.size() > 0) {
            LitePal.deleteAll(BranchGroupInfo.class);
            //return;
        }*/
        String branchUrl = "https://mtask.motrex.co.kr/branches";
        HttpUtil.getInstance().makeJsonObjectRequest(branchUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean result = response.getBoolean("result");
                    if (result) {
                        JSONArray branchList =  response.getJSONArray("data");
                        LogUtil.d(TAG, branchList.toString());
                        for (int i = 0; i < branchList.length(); i ++) {
                            JSONObject object = branchList.getJSONObject(i);
                            BranchGroupInfo branchGroupInfo = new BranchGroupInfo();
                            branchGroupInfo.setBranchId(object.getString("id"));
                            branchGroupInfo.setOrderNo(object.getString("orderNo"));
                            branchGroupInfo.setName(object.getString("name"));
                            branchGroupInfo.save();
                        }
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
}

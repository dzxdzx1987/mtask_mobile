package com.example.mtask_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.com.example.mtask_mobile.vo.Task;
import com.example.mtask_mobile.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyTaskFragment extends Fragment {
    private final static String TAG = MyTaskFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    private TaskAdapter mAdapter;

    private List<Task> taskList = new ArrayList<>();

    public MyTaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyTaskFragment newInstance(String param1, String param2) {
        MyTaskFragment fragment = new MyTaskFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mGridLayoutManager = new GridLayoutManager(MTaskApplication.getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        requestMyTaskList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void requestMyTaskList() {
        Map<String, String> headers = new HashMap<String, String>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MTaskApplication.getContext());
        String cookie = prefs.getString("cookie", null);
        headers.put("Cookie", cookie);
        HttpUtil.getInstance().makeJsonObjectRequestWithHeaders("https://mtask.motrex.co.kr/my-tasks", headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d(TAG, response.toString());
                        try {
                            boolean res = response.getBoolean("result");
                            if (res) {
                                taskList.clear();
                                JSONArray myTaskList =  response.getJSONArray("data");
                                LogUtil.d(TAG, myTaskList.toString());
                                for (int i = 0; i < myTaskList.length(); i ++) {
                                    JSONObject object = myTaskList.getJSONObject(i);
                                    String taskName = object.getString("title");
                                    String taskDesc = object.getString("desc");

                                    Task task = new Task(taskName, "", android.text.Html.fromHtml(taskDesc).toString());
                                    taskList.add(task);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.e(TAG, error.getMessage());
                    }
                });
    }
}

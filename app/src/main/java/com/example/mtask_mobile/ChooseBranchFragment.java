package com.example.mtask_mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.util.HttpUtil;
import com.example.mtask_mobile.util.Utility;
import com.example.mtask_mobile.vo.BranchGroupInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ChooseBranchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = ChooseBranchFragment.class.getSimpleName();

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private Button refreshButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<BranchGroupInfo> branchGroupInfoList;

    public ChooseBranchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_branch, container, false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        refreshButton = view.findViewById(R.id.refresh_branch_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(BranchGroupInfo.class);
                requestFromServer();
            }
        });
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BranchGroupInfo branchGroupInfo = branchGroupInfoList.get(position);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.putString("branchId", branchGroupInfo.getBranchId());
                editor.apply();

                Toast.makeText(getContext(), "You selected " + branchGroupInfo.getName(), Toast.LENGTH_SHORT).show();
                Intent returnIntent  = new Intent();

                returnIntent.putExtra("branchGroupInfo", branchGroupInfo);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();

            }
        });

        requestBranchGroup();
        super.onActivityCreated(savedInstanceState);
    }

    private void requestBranchGroup() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String branchId = prefs.getString("branchId", null);
        titleText.setText("Select Branch");
        branchGroupInfoList = LitePal.findAll(BranchGroupInfo.class);
        if (branchGroupInfoList.size() > 0) {
            dataList.clear();
            for (BranchGroupInfo branchGroupInfo : branchGroupInfoList) {
                dataList.add(branchGroupInfo.getName());
                if (branchGroupInfo.getBranchId().equals(branchId)) {
                    titleText.setText(branchGroupInfo.getName());
                }
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);

        } else {
            requestFromServer();
        }
    }

    private void requestFromServer() {
        showProgressDialog();
        String branchUrl = "https://mtask.motrex.co.kr/branches";
        HttpUtil.getInstance().makeJsonObjectRequest(branchUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean result = Utility.handleBranchGroupList(response);
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            requestBranchGroup();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e(TAG, error.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "load failure!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}

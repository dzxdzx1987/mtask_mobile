package com.example.mtask_mobile.board.view;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.mtask_mobile.board.adapter.BoardUserAdapter;
import com.example.mtask_mobile.R;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.repository.UserRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardInfoFragment extends Fragment {
    private final static String TAG = BoardInfoFragment.class.getSimpleName();

    private ArrayList<String> mGroupList;
    //item数据
    private ArrayList<ArrayList<String>> mItemSet;

    private Context mContext;

    private ExpandableListView mExpandableListView;

    private String mBoardId;

    private TextView mBoardUserCnt;

    private BoardUserAdapter mAdapter;

    private final static int MESSAGE_GET_DATA = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_GET_DATA:
                    mAdapter = new BoardUserAdapter(mContext, mGroupList, mItemSet);
                    mExpandableListView.setAdapter(mAdapter);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private UserRepository.IUserCallback userCallback = new UserRepository.IUserCallback() {
        @Override
        public void onSuccess(JSONObject object) {
            ArrayList<String> itemList1 = new ArrayList<>();

            try {
                mItemSet.clear();
                JSONArray userList =  object.getJSONArray("data");
                LogUtil.d(TAG, userList.toString());
                for (int i = 0; i < userList.length(); i ++) {
                    JSONObject obj = userList.getJSONObject(i);
                    String name = obj.getString("name");
                    itemList1.add(name);
                }
                mItemSet.add(itemList1);
                //mAdapter.notifyDataSetChanged();
                Message message= new Message();
                message.what = MESSAGE_GET_DATA;
                mHandler.sendMessage(message);

            } catch (JSONException e) {
                LogUtil.e(TAG, e.getMessage());
            }

        }

        @Override
        public void onFailure(int errorCode) {

        }
    };

    public BoardInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_info, container, false);

        mGroupList = new ArrayList<>();
        mGroupList.add("Board User");
        mItemSet = new ArrayList<>();

        mExpandableListView = view.findViewById(R.id.expand_lv);
        mBoardUserCnt = view.findViewById(R.id.board_user_cnt);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext =context;
        //initData();
        mBoardId = getActivity().getIntent().getStringExtra("boardId");
        LogUtil.d(TAG, "request board id:" + mBoardId);
        UserRepository.getInstance().requestBranchUserListByBoardId(mBoardId, userCallback);

    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy");
        super.onDestroy();
    }
}

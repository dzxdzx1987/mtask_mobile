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
import com.example.mtask_mobile.repository.BoardRepository;
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
    private TextView mTotalCntTv;
    private TextView mOpenCntTv;
    private TextView mUnderReviewCntTv;
    private TextView mOngoingCntTv;
    private TextView mReviewCntTv;
    private TextView mPendingCntTv;
    private TextView mClosedCntTv;
    private final static int MESSAGE_GET_DATA = 0;
    private final static int MESSAGE_GET_STATUS = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_GET_DATA:
                    mAdapter = new BoardUserAdapter(mContext, mGroupList, mItemSet);
                    mExpandableListView.setAdapter(mAdapter);
                    break;
                case MESSAGE_GET_STATUS:
                    try {
                        JSONObject object = (JSONObject) msg.obj;
                        mTotalCntTv.setText(object.getString("total"));
                        mOpenCntTv.setText(object.getString("open"));
                        mUnderReviewCntTv.setText(object.getString("underReview"));
                        mOngoingCntTv.setText(object.getString("doing"));
                        mReviewCntTv.setText(object.getString("review"));
                        mPendingCntTv.setText(object.getString("pending"));
                        mClosedCntTv.setText(object.getString("closed"));
                    } catch (JSONException e) {
                        LogUtil.e(TAG, e.getMessage());
                    }

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

    private BoardRepository.IBoardCallback boardCallback = new BoardRepository.IBoardCallback() {
        @Override
        public void onSuccess(JSONObject object) {
            try {
                JSONArray status = object.getJSONArray("data");
                LogUtil.d(TAG, status.getJSONObject(0).toString());
                JSONObject obj = status.getJSONObject(0);
                Message message = new Message();
                message.what = MESSAGE_GET_STATUS;
                message.obj = obj;
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

        mTotalCntTv = view.findViewById(R.id.board_total_tasks_cnt);
        mOpenCntTv = view.findViewById(R.id.board_open_tasks_cnt);
        mUnderReviewCntTv = view.findViewById(R.id.board_under_review_tasks_cnt);
        mOngoingCntTv = view.findViewById(R.id.board_ongoing_tasks_cnt);
        mReviewCntTv = view.findViewById(R.id.board_review_tasks_cnt);
        mPendingCntTv = view.findViewById(R.id.board_pending_tasks_cnt);
        mClosedCntTv = view.findViewById(R.id.board_close_tasks_cnt);

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
        BoardRepository.getInstance().requestBoardStatusList(mBoardId, boardCallback);

    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy");
        super.onDestroy();
    }
}

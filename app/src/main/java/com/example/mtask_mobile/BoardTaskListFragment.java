package com.example.mtask_mobile;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.repository.TaskRepository;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardTaskListFragment extends Fragment {
    private final static String TAG = BoardTaskListFragment.class.getSimpleName();
    private String mBoardId;
    private TaskRepository.ITaskCallback taskCallback = new TaskRepository.ITaskCallback() {
        @Override
        public void onSuccess(JSONObject object) {
            LogUtil.d(TAG, "task list: " + object.toString());
        }

        @Override
        public void onFailure(int errorCode) {

        }
    };

    public BoardTaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board_task_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        mBoardId = getActivity().getIntent().getStringExtra("boardId");
        LogUtil.d(TAG, "request board id:" + mBoardId);
        TaskRepository.getInstance().requestTaskListByBoardId(mBoardId, taskCallback);
        super.onAttach(context);
    }
}

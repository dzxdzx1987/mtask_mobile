package com.example.mtask_mobile;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.repository.TaskRepository;
import com.example.mtask_mobile.vo.TaskInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardTaskListFragment extends Fragment {
    private final static String TAG = BoardTaskListFragment.class.getSimpleName();
    private String mBoardId;
    private List<TaskInfo> firstLevelTaskList = new ArrayList<>();;

    private RecyclerView mRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    private BoardTaskAdapter mAdapter;

    private TaskRepository.ITaskCallback taskCallback = new TaskRepository.ITaskCallback() {
        @Override
        public void onSuccess(JSONObject object) {
            LogUtil.d(TAG, "task list: " + object.toString());
            LitePal.deleteAll(TaskInfo.class);
            try {
                JSONArray taskList =  object.getJSONArray("data");
                for (int i = 0; i < taskList.length(); i ++) {
                    JSONObject obj = taskList.getJSONObject(i);
                    String boardId = obj.getString("boardId");
                    String boardName = obj.getString("boardName");
                    String parentId = obj.getString("parentId");
                    String uuid = obj.getString("id");
                    int orderNo = obj.getInt("orderNo");
                    boolean isFolder = obj.getBoolean("isFolder");
                    boolean isPrivate = obj.getBoolean("isPrivate");
                    boolean allowPrivate = obj.getBoolean("allowPrivate");
                    int status = obj.getInt("status");
                    String title = obj.getString("title");
                    String desc = obj.getString("desc");
                    String constructorName = obj.getString("constructorName");
                    String createdDateTime = obj.getString("createdDateTime");

                    TaskInfo taskInfo = new TaskInfo();
                    taskInfo.setBoardId(boardId);
                    taskInfo.setBoardName(boardName);
                    taskInfo.setParentId(parentId);
                    taskInfo.setUuid(uuid);
                    taskInfo.setOrderNo(orderNo);
                    taskInfo.setFolder(isFolder);
                    taskInfo.setPrivate(isPrivate);
                    taskInfo.setStatus(status);
                    taskInfo.setAllowPrivate(allowPrivate);
                    taskInfo.setTitle(title);
                    taskInfo.setDesc(android.text.Html.fromHtml(desc).toString());
                    taskInfo.setConstructorName(constructorName);
                    taskInfo.setCreatedDateTime(createdDateTime);

                    if (parentId == null || parentId.equalsIgnoreCase("null")) {
                        firstLevelTaskList.add(taskInfo);
                        LogUtil.d(TAG, "firstLevelTaskList: " + firstLevelTaskList.toString());
                    }

                    taskInfo.save();
                }
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                LogUtil.e(TAG, e.getMessage());
            }

        }

        @Override
        public void onFailure(int errorCode) {
            LogUtil.e(TAG, "error code: " + errorCode);
        }
    };

    public BoardTaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_task_list, container, false);

        mRecyclerView = view.findViewById(R.id.board_task_list_recycler_view);
        mGridLayoutManager = new GridLayoutManager(MTaskApplication.getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new BoardTaskAdapter(firstLevelTaskList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        mBoardId = getActivity().getIntent().getStringExtra("boardId");
        LogUtil.d(TAG, "request board id:" + mBoardId);
        TaskRepository.getInstance().requestTaskListByBoardId(mBoardId, taskCallback);
        super.onAttach(context);
    }
}

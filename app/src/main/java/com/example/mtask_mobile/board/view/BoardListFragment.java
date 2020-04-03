package com.example.mtask_mobile.board.view;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtask_mobile.board.adapter.BoardAdapter;
import com.example.mtask_mobile.MTaskApplication;
import com.example.mtask_mobile.R;
import com.example.mtask_mobile.com.example.mtask.util.LogUtil;
import com.example.mtask_mobile.repository.BoardRepository;
import com.example.mtask_mobile.vo.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardListFragment extends Fragment {
    private final static String TAG = BoardListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    private BoardAdapter mAdapter;

    private List<Board> mBoardList = new ArrayList<>();

    private BoardRepository.IBoardCallback boardCallback = new BoardRepository.IBoardCallback() {
        @Override
        public void onSuccess(JSONObject object) {
            try {
                JSONArray myTaskList =  object.getJSONArray("data");
                LogUtil.d(TAG, myTaskList.toString());
                for (int i = 0; i < myTaskList.length(); i ++) {
                    JSONObject obj = myTaskList.getJSONObject(i);
                    String uuid = obj.getString("id");
                    String name = obj.getString("name");
                    String desc = obj.getString("desc");
                    String descPlainText = obj.getString("descPlainText");
                    String color = obj.getString("color");
                    boolean isFavorites = obj.getBoolean("isFavorites");
                    String constructorName = obj.getString("constructorName");
                    int taskCnt = obj.getInt("taskCnt");
                    String createdDateTime = obj.getString("createdDateTime");
                    String updatedDateTime = obj.getString("updatedDateTime");

                    if (descPlainText == null || descPlainText.equals("")) {
                        descPlainText = android.text.Html.fromHtml(desc).toString();
                    }

                    Board board = new Board(uuid, name, desc, descPlainText, color, isFavorites, constructorName, taskCnt, createdDateTime, updatedDateTime);
                    mBoardList.add(board);
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


    public BoardListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_list, container, false);

        mRecyclerView = view.findViewById(R.id.board_list_recycler_view);
        mGridLayoutManager = new GridLayoutManager(MTaskApplication.getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new BoardAdapter(mBoardList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        requestBoardList();
    }

    private void requestBoardList() {
        BoardRepository.getInstance().requestBoardList(boardCallback);
    }
}

package com.example.mtask_mobile.board.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.mtask_mobile.CutomView;
import com.example.mtask_mobile.R;
import com.example.mtask_mobile.board.adapter.BoardUserAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardProgressFragment extends Fragment {

    private ArrayList<String> mGroupList;
    //item数据
    private ArrayList<ArrayList<String>> mItemSet;

    private Context mContext;

    private ExpandableListView mExpandableListView;

    private BoardUserAdapter mAdapter;

    private CutomView mCutomView;

    public BoardProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_progress, container, false);

        mExpandableListView = view.findViewById(R.id.expand_lv);
        mAdapter = new BoardUserAdapter(mContext, mGroupList, mItemSet);
        mExpandableListView.setAdapter(mAdapter);

        mCutomView = view.findViewById(R.id.custom_view);

        mCutomView.setmTextLeft("ProgressFragment");

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
        initData();

    }

    private void initData() {
        mGroupList = new ArrayList<>();
        mGroupList.add("我的家人");
        mGroupList.add("我的朋友");
        mGroupList.add("黑名单");
        mGroupList.add("热销推荐");
        mGroupList.add("菜品");
        mGroupList.add("主食");
        mItemSet = new ArrayList<>();
        ArrayList<String> itemList1 = new ArrayList<>();
        itemList1.add("大妹");
        itemList1.add("二妹");
        itemList1.add("三妹");
        ArrayList<String> itemList2 = new ArrayList<>();
        itemList2.add("大美");
        itemList2.add("二美");
        itemList2.add("三美");
        ArrayList<String> itemList3 = new ArrayList<>();
        itemList3.add("狗蛋");
        itemList3.add("二丫");
        ArrayList<String> itemList4 = new ArrayList<>();
        itemList4.add("热销零食1");
        itemList4.add("热销零食2");
        itemList4.add("热销零食3");
        ArrayList<String> itemList5 = new ArrayList<>();
        itemList5.add("小菜一碟1");
        itemList5.add("小菜一碟2");
        itemList5.add("小菜一碟3");
        ArrayList<String> itemList6 = new ArrayList<>();
        itemList6.add("馒头，嗷~");
        itemList6.add("啤酒饮料矿泉水");
        mItemSet.add(itemList1);
        mItemSet.add(itemList2);
        mItemSet.add(itemList3);
        mItemSet.add(itemList4);
        mItemSet.add(itemList5);
        mItemSet.add(itemList6);
    }
}

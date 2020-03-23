package com.example.mtask_mobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class BoardFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    private BoardInfoFragment boardInfoFragment = new BoardInfoFragment();
    private BoardTaskListFragment boardTaskListFragment = new BoardTaskListFragment();
    private BoardProgressFragment boardProgressFragment = new BoardProgressFragment();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_board_info:
                    transaction.replace(R.id.frame_layout, boardInfoFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_board_task_list:
                    transaction.replace(R.id.frame_layout, boardTaskListFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_board_progress:
                    transaction.replace(R.id.frame_layout, boardProgressFragment).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };


    public BoardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        fragmentManager = getChildFragmentManager();
        bottomNavigationView =view.findViewById(R.id.navigation_board);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, boardInfoFragment).commitAllowingStateLoss();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

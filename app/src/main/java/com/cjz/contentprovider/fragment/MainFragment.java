package com.cjz.contentprovider.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjz.contentprovider.MainActivity;
import com.cjz.contentprovider.R;
import com.cjz.contentprovider.SmsInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MainActivity main;
    private LinearLayout mLlList;
    private SwipeRefreshLayout mSwipeRefresh;
    private String text = "";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        main= (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mLlList = (LinearLayout) view.findViewById(R.id.ll_list);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeColors(Color.BLUE);
        mSwipeRefresh.setProgressViewEndTarget(true,100);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ActivityCompat.requestPermissions(main, new String[]{Manifest.permission.READ_SMS}, 1);
                refreshView();
                mSwipeRefresh.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    private List<SmsInfo> smsInfos;
    public void refreshView(){
        smsInfos = main.getSmsInfos();
        mLlList.removeAllViews();
        for (int i = 0; i < smsInfos.size(); i++) {
            View view = LayoutInflater.from(main).inflate(R.layout.main_item, null);
            TextView textView = view.findViewById(R.id.tv_sms);
            text += "手机号码：" + this.smsInfos.get(i).getAddress() + "\n";
            text += "短信内容：" + this.smsInfos.get(i).getBody() + "\n\n";
            textView.setText(text);
            mLlList.addView(view);
            text = "";
        }
    }
}

package com.example.cheatgz.lostandfoundsystem;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.yymstaygold.lostandfound.client.ClientDelegation;
import com.yymstaygold.lostandfound.client.entity.Lost;
import com.yymstaygold.lostandfound.client.util.match.MatchInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class message extends AppCompatActivity {
    private ExpandableListView listView1;

    ArrayList<String> lostProperty;//备注名
    ArrayList<ArrayList<String>> matchResult;//物品详细描述

    //限制最多只展开一项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    setListView1();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                ThisApplication application = (ThisApplication) getApplication();
                ArrayList<MatchInfo> pairs = ClientDelegation.checkNewFounds(application.getUserId());
                HashMap<Integer, ArrayList<Integer>> groups = new HashMap<>();
                for (MatchInfo pair : pairs) {
                    int lostId = pair.getLostId();
                    int foundId = pair.getFoundId();
                    if (groups.containsKey(lostId)) {
                        groups.get(lostId).add(foundId);
                    } else {
                        ArrayList<Integer> founds = new ArrayList<>();
                        founds.add(foundId);
                        groups.put(lostId, founds);
                    }
                }
                lostProperty = new ArrayList<>();
                matchResult = new ArrayList<>();
                int count = 0;
                for (int lostId : groups.keySet()) {
                    lostProperty.add(getLostNameById(lostId));
                    ArrayList<String> founds = new ArrayList<>();
                    for (int foundId : groups.get(lostId)) {
                        founds.add(getFoundNameById(foundId));
                    }
                    matchResult.add(founds);
                }
            }

            private String getLostNameById(int lostId) {
                return ClientDelegation.downloadLostInfo(lostId).getLostName();
            }

            private String getFoundNameById(int foundId) {
                return ClientDelegation.downloadFoundInfo(foundId).getFoundName();
            }
        }).start();
    }

    protected void setListView1(){
        final ExpandableListAdapter expandableListAdapter1=new BaseExpandableListAdapter() {

            private TextView getTextView(){
                TextView textView1=new TextView(message.this);
                textView1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.LEFT);
                textView1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.MATCH_PARENT));
                return textView1;
            }

            @Override
            public int getGroupCount() {
                return lostProperty.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return matchResult.get(groupPosition).size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return matchResult.get(groupPosition).size();
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return matchResult.get(groupPosition).get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                CardView cardView1=new CardView(message.this);
                TextView textView1=getTextView();
                textView1.setText(lostProperty.get(groupPosition));
                textView1.setHeight(200);
                textView1.setGravity(Gravity.CENTER_VERTICAL);
                cardView1.addView(textView1);
                return cardView1;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                TextView textView1=new TextView(message.this);
                textView1.setHeight(100);
                textView1.setTextColor(0xFFCAE1FF);
                textView1.setGravity(Gravity.CENTER_VERTICAL);
                textView1.setTextSize(13);
                textView1.setText((this.getChild(groupPosition,childPosition).toString()));
                return textView1;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };

        listView1=(ExpandableListView)findViewById(R.id.result);
        listView1.setAdapter(expandableListAdapter1);
        listView1.setGroupIndicator(null);

        //设置最多只能打开一项group
        listView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup=-1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition!=previousGroup)
                    listView1.collapseGroup(previousGroup);
                previousGroup=groupPosition;
            }
        });

        //设置子项点击事件
        listView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Intent intent1=new Intent(message.this,match_property.class);
                startActivity(intent1);
                return true;
            }
        });
    }
}


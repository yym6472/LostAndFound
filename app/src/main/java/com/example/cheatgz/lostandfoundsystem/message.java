package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class message extends AppCompatActivity {
    private ExpandableListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        setListView1();
    }

    protected void setListView1(){
        final ExpandableListAdapter expandableListAdapter1=new BaseExpandableListAdapter() {

            String[] lostProperty=new String[]{"钱包","身份证","相机"};//备注名
            String[][] matchResult=new String[][]{{"红色皮革钱包","绿色棉布钱包2","黄色丝绸钱包"},
                    {"江苏省身份证","浙江省身份证","北京市身份证","山东省身份证"},
                    {"蓝色拍立得相机"}};//物品详细描述

            //限制最多只展开一项


            private TextView getTextView(){
                TextView textView1=new TextView(message.this);
                textView1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.LEFT);
                textView1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.MATCH_PARENT));
                return textView1;
            }

            @Override
            public int getGroupCount() {
                return lostProperty.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return matchResult[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return matchResult[groupPosition].length;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return matchResult[groupPosition][childPosition];
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
                textView1.setText(lostProperty[groupPosition]);
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


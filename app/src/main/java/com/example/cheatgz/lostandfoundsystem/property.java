package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.yymstaygold.lostandfound.client.ClientDelegation;
import com.yymstaygold.lostandfound.client.entity.Found;
import com.yymstaygold.lostandfound.client.entity.Lost;

import java.util.ArrayList;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class property extends BaseActivity {
    private ListView listView1;
    private ListView listView2;
    private String[] string1={};//我的失物集
    private String[] string2={};//我的拾物集
    private Found[] founds;
    private Lost[] losts;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property);

        handler = new Handler();
        setListView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ThisApplication application = (ThisApplication) getApplication();
                ArrayList<Integer> foundIds = ClientDelegation.checkUserFound(application.getUserId());
                ArrayList<Integer> lostIds = ClientDelegation.checkUserLost(application.getUserId());
                System.out.println("LOST_COUNT: " + lostIds.size());
                System.out.println("FOUND_COUNT: " + foundIds.size());
                string1 = new String[lostIds.size()];
                string2 = new String[foundIds.size()];
                founds = new Found[foundIds.size()];
                losts = new Lost[lostIds.size()];
                for (int i = 0; i < foundIds.size(); ++i) {
                    founds[i] = ClientDelegation.downloadFoundInfo(foundIds.get(i));
                    string2[i] = founds[i].getFoundName();
                }
                for (int i = 0; i < lostIds.size(); ++i) {
                    losts[i] = ClientDelegation.downloadLostInfo(lostIds.get(i));
                    string1[i] = losts[i].getLostName();
                }
                handler.post(runnableUi);
            }
        }).start();
    }
    protected void setListView(){
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(property.this,R.layout.text_view,string1);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(property.this,R.layout.text_view,string2);
        listView1=(ListView)findViewById(R.id.myLost);
        listView2=(ListView)findViewById(R.id.myFound);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent1=new Intent(property.this,change_property.class);
                startActivity(intent1);
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent2=new Intent(property.this,change_property.class);
                startActivity(intent2);
            }
        });
    }

    Runnable runnableUi=new Runnable(){
        @Override
        public void run() {
            setListView();
        }
    };
}

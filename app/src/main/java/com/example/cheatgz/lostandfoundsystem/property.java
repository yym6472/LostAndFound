package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class property extends AppCompatActivity {
    private ListView listView1;
    private ListView listView2;
    private String[] string1={"香蕉","橘子","苹果"};//我的失物集
    private String[] string2={"足球","篮球","乒乓球","羽毛球","排球"};//我的拾物集
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property);

        setListView();
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
}

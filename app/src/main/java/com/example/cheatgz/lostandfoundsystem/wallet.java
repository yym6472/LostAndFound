package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class wallet extends BaseActivity {
    private Button btn1;
    private Button btn2;
    private TextView textView1;//余额
    private TextView textView2;//收入
    private TextView textView3;//支出
    private EditText editText;//输入金额
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private int balance=100;
    private int earn=10;
    private int spend=10;
    private int money;
    String money_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        btn1=(Button)findViewById(R.id.charge);
        btn2=(Button)findViewById(R.id.charge2);
        textView1=(TextView)findViewById(R.id.balance);
        textView2=(TextView)findViewById(R.id.earn);
        textView3=(TextView)findViewById(R.id.spend);
        editText=(EditText)findViewById(R.id.money);

        linearLayout1=(LinearLayout)findViewById(R.id.main);
        linearLayout2=(LinearLayout)findViewById(R.id.dialog);
        linearLayout2.setVisibility(View.GONE);

        textView1.setText("余额: "+balance);
        textView2.setText("收入: "+earn);
        textView3.setText("支出: "+spend);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent1=new Intent(wallet.this,home_page.class);
                //startActivity(intent1);
                linearLayout1.setBackgroundColor(0xFF969696);
                linearLayout1.setAlpha((float) 0.1);
                linearLayout2.setVisibility(View.VISIBLE);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:为解决充值金额为空的情况
                money_=editText.getText().toString();
                money=Integer.parseInt(money_);
                if(money_==null||money_.length()<=0){
                    android.widget.Toast.makeText(wallet.this, "充值金额不能为空", android.widget.Toast.LENGTH_SHORT).show();

                }
                else if(money==0){
                    android.widget.Toast.makeText(wallet.this, "充值金额不能为0", android.widget.Toast.LENGTH_SHORT).show();
                }else{
                    linearLayout1.setBackgroundColor(0xFFFFFFFF);
                    linearLayout1.setAlpha((float) 1);
                    linearLayout2.setVisibility(View.GONE);
                }
            }
        });
    }
}

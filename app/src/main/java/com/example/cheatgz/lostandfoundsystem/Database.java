package com.example.cheatgz.lostandfoundsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper{
    public static String createTable_UserLocate="create table user_location (UserID integer , XLocate double,YLocate double,Time text primary key)";
    private Context mContext;
    public Database(Context context , String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context,name,cursorFactory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTable_UserLocate);
        Toast.makeText(mContext,"succeed",Toast.LENGTH_SHORT).show();
}

@Override
public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
        }

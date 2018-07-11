package com.example.cheatgz.lostandfoundsystem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * A SQLite helper class.
 * Mainly from https://blog.csdn.net/wzy_1988/article/details/51374979.
 */
public class LocationInfoHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lfs.db";

    private static final String SQL_CREATE_LOCATION_INFO_TABLE =
            "CREATE TABLE " + LocationInfoTable.TABLE_NAME + "(" +
                    LocationInfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LocationInfoTable.COLUMN_NAME_USER_ID + " INTEGER, " +
                    LocationInfoTable.COLUMN_NAME_TIME + " INTEGER, " +
                    LocationInfoTable.COLUMN_NAME_POSITION_X + " DOUBLE, " +
                    LocationInfoTable.COLUMN_NAME_POSITION_Y + " DOUBLE " +
                    ")";

    private static final String SQL_DELETE_LOCATION_INFO_TABLE =
            "DROP TABLE IF EXISTS " + LocationInfoTable.TABLE_NAME;

    private static LocationInfoHelper mInstance;

    private LocationInfoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static LocationInfoHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LocationInfoHelper.class) {
                if (mInstance == null) {
                    mInstance = new LocationInfoHelper(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_LOCATION_INFO_TABLE);
        onCreate(sqLiteDatabase);
    }

    public class LocationInfoTable implements BaseColumns {
        public static final String TABLE_NAME = "LocationInfo";
        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_POSITION_X = "positionX";
        public static final String COLUMN_NAME_POSITION_Y = "positionY";
    }
}

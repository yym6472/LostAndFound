package com.yymstaygold.lostandfound.client.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.example.cheatgz.lostandfoundsystem.R;
import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.db.LocationInfoHelper;
import com.example.cheatgz.lostandfoundsystem.util.AMapInteractiveHelper;
import com.victor.library.wheelview.WheelView;
import com.yymstaygold.lostandfound.client.Adapter.WheelViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

public class TrackFragment extends Fragment {
    private static final String TAG = "TrackFragment";

    private WheelView chooseDateView = null;
    private WheelView chooseTimeView = null;
    private MapView mMapView = null;

    private ArrayList<Date> times = new ArrayList<>();
    private ArrayList<LatLng> latLngs = new ArrayList<>();

    private TreeMap<String, TreeMap<String, LatLng>> trackInfo = new TreeMap<>();

    private ArrayList<String> dateList;
    private ArrayList<String> timeList;

    public TrackFragment() {}

    public static TrackFragment newInstance() {
        return new TrackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationInfoHelper helper = LocationInfoHelper.getInstance(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                LocationInfoHelper.LocationInfoTable.TABLE_NAME,
                new String[] {
                        LocationInfoHelper.LocationInfoTable.COLUMN_NAME_TIME,
                        LocationInfoHelper.LocationInfoTable.COLUMN_NAME_POSITION_X,
                        LocationInfoHelper.LocationInfoTable.COLUMN_NAME_POSITION_Y
                },
                LocationInfoHelper.LocationInfoTable.COLUMN_NAME_USER_ID + "=?",
                new String[] {((ThisApplication)getActivity().getApplication()).getUserId() + ""},
//                new String[] {"24"},
                null, null,
                LocationInfoHelper.LocationInfoTable.COLUMN_NAME_TIME,
                null);

        while(cursor.moveToNext()) {
            Date date = new Date(cursor.getLong(0));
            Log.d(TAG, "onCreate: " + date);
            times.add(date);
            latLngs.add(new LatLng(cursor.getDouble(2), cursor.getDouble(1)));
        }
        // TODO: 处理数据为空时候的情况

        DateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm");
        for (int i = times.size() - 1; i >= 0; --i) {
            Date currentDateTime = times.get(i);
            String date = dateFormatDate.format(currentDateTime);
            String time = dateFormatTime.format(currentDateTime);
            if (!trackInfo.containsKey(date)) {
                trackInfo.put(date, new TreeMap<String, LatLng>());
            }
            TreeMap<String, LatLng> subMap = trackInfo.get(date);
            subMap.put(time, latLngs.get(i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_track, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = getView().findViewById(R.id.track_map);
        mMapView.onCreate(savedInstanceState);

        chooseDateView = getView().findViewById(R.id.choose_date);
        chooseTimeView = getView().findViewById(R.id.choose_time);

        final AMap aMap = mMapView.getMap();
        aMap.getUiSettings().setScaleControlsEnabled(true);
        AMapInteractiveHelper.drawPoints(aMap, latLngs);
        AMapInteractiveHelper.drawLines(aMap, latLngs);

        dateList = new ArrayList<>(trackInfo.keySet());
        Collections.reverse(dateList);
        timeList = new ArrayList<>(trackInfo.get(dateList.get(0)).keySet());
        Collections.reverse(timeList);

        chooseDateView.setAdapter(new WheelViewAdapter(dateList));
        chooseDateView.setMode(WheelView.getStartModeInstance(chooseDateView));
        chooseDateView.setWheelScrollListener(new WheelView.WheelScrollListener() {
            @Override
            public void onChanged(WheelView wheelView, int selected, Object bean) {
                timeList = new ArrayList<>(trackInfo.get(dateList.get(selected)).keySet());
                Collections.reverse(timeList);
                chooseTimeView.setAdapter(new WheelViewAdapter(timeList));
                chooseTimeView.setCurrItem(0);
                AMapInteractiveHelper.animateJumpTo(aMap, trackInfo.get(dateList.get(selected)).get(timeList.get(0)));
            }
        });
        chooseTimeView.setAdapter(new WheelViewAdapter(timeList));
        chooseTimeView.setMode(WheelView.getStartModeInstance(chooseTimeView));
        chooseTimeView.setWheelScrollListener(new WheelView.WheelScrollListener() {
            @Override
            public void onChanged(WheelView wheelView, int selected, Object bean) {
                AMapInteractiveHelper.animateJumpTo(aMap, trackInfo.get(dateList.get(chooseDateView.getSelectedItem())).get(timeList.get(selected)));
            }
        });
        AMapInteractiveHelper.animateJumpTo(aMap, trackInfo.get(dateList.get(0)).get(timeList.get(0)));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

}

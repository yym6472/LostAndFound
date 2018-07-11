package com.example.cheatgz.lostandfoundsystem.util;

import android.graphics.Color;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;

import java.util.List;

public class AMapInteractiveHelper {

    public static void drawPoints(AMap aMap, List<LatLng> latLngs) {
        for (LatLng latLng : latLngs) {
            aMap.addMarker(new MarkerOptions().position(latLng));
        }
    }

    public static void drawLines(AMap aMap, List<LatLng> latLngs) {
        aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
    }

    public static void animateJumpTo(AMap aMap, LatLng jumpTo) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(jumpTo, aMap.getCameraPosition().zoom, 0, 0)));
    }
}

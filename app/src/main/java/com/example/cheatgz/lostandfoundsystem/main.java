package com.example.cheatgz.lostandfoundsystem;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.cheatgz.lostandfoundsystem.overlay.PoiOverlay;
import com.example.cheatgz.lostandfoundsystem.util.Constants;
import com.example.cheatgz.lostandfoundsystem.util.ToastUtil;


import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class main extends BaseActivity implements
        AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,
        PoiSearch.OnPoiSearchListener{
    private MapView mMapView;
    private AMap mAMap;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    private String mKeyWords = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 1;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private TextView mKeywordsTextView;
    private Marker mPoiMarker;
    private ImageView mCleanKeyWords;

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE_INPUTTIPS = 101;
    public static final int RESULT_CODE_KEYWORDS = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mCleanKeyWords = (ImageView)findViewById(R.id.clean_keywords);
        mCleanKeyWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeywordsTextView.setText("");
                mAMap.clear();
                mCleanKeyWords.setVisibility(View.GONE);
            }
        });
        initMap(savedInstanceState);//加载地图

        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        ArrayList<String> permissionsList = new ArrayList<>();
        for (int i = 0; i < permissions.length; ++i) {
            if (ContextCompat.checkSelfPermission(main.this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permissions[i]);
            }
        }
        if (permissionsList.size() != 0) {
            String[] permissionsToApply = new String[permissionsList.size()];
            for (int i = 0; i < permissionsList.size(); ++i) {
                permissionsToApply[i] = permissionsList.get(i);
            }
            ActivityCompat.requestPermissions(main.this, permissionsToApply, 0);
        }

        btn1=(Button)findViewById(R.id.set);
        btn2=(Button)findViewById(R.id.message);
        btn3=(Button)findViewById(R.id.lost);
        btn4=(Button)findViewById(R.id.found);
        //TODO:链接数据库，判断有新的匹配信息时，添加new小红点
        Badge badge=new QBadgeView(this);
            badge.bindTarget(btn2);
            badge.setBadgeText("new");
            badge.setBadgeTextSize(6,true);
        buttonEvent(badge);
    }

    protected void buttonEvent(final Badge badge){


        //点击设置按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(main.this,home_page.class);
                startActivity(intent1);
            }
        });

        //点击匹配信息按钮
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(main.this,message.class);
                startActivity(intent2);
                badge.hide(true);
            }
        });

        //点击捡到物品按钮
        btn3.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(main.this,lost1.class);
                startActivity(intent3);
            }
        }));

        //点击丢失物品按钮
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(main.this,found1.class);
                startActivity(intent4);
            }
        });
    }
    protected void initMap(Bundle savedInstanceState){
        UiSettings mUiSettings;//定义一个UiSettings对象

        mMapView=(MapView)findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象

        setUpMap();
        mKeywordsTextView = (TextView) findViewById(R.id.main_keywords);
        mKeywordsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, InputTipsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //实现定位蓝点
        MyLocationStyle myLocationStyle;
        myLocationStyle=new MyLocationStyle();
        myLocationStyle.interval(2000);
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setMyLocationEnabled(true);//设置定位蓝点是否可见

        //地图初始显示设置
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));//地图初始显示比例
        myLocationStyle.showMyLocation(true);//地图初始位置

        //控件设置
        mUiSettings.setCompassEnabled(true);//指南针
        mUiSettings.setScaleControlsEnabled(true);//比例尺
        mUiSettings.setMyLocationButtonEnabled(true);//定位按钮
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    /**
     * 设置页面监听
     */
    private void setUpMap() {
        mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
        mAMap.getUiSettings().setAllGesturesEnabled(false);
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + mKeyWords);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keywords) {
        showProgressDialog();// 显示进度框
        currentPage = 1;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keywords, "", Constants.DEFAULT_CITY);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        // 设置查第一页
        query.setPageNum(currentPage);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        return view;
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.show(main.this, infomation);

    }


    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        mAMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(mAMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.show(main.this,
                                R.string.no_result);
                    }
                }
            } else {
                ToastUtil.show(main.this,
                        R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this, rCode);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
        // TODO Auto-generated method stub

    }

    /**
     * 输入提示activity选择结果后的处理逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_INPUTTIPS && data
                != null) {
            mAMap.clear();
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);
            if (tip.getPoiID() == null || tip.getPoiID().equals("")) {
                doSearchQuery(tip.getName());
            } else {
                addTipMarker(tip);
            }
            mKeywordsTextView.setText(tip.getName());
            if(!tip.getName().equals("")){
                mCleanKeyWords.setVisibility(View.VISIBLE);
            }
        } else if (resultCode == RESULT_CODE_KEYWORDS && data != null) {
            mAMap.clear();
            String keywords = data.getStringExtra(Constants.KEY_WORDS_NAME);
            if(keywords != null && !keywords.equals("")){
                doSearchQuery(keywords);
            }
            mKeywordsTextView.setText(keywords);
            if(!keywords.equals("")){
                mCleanKeyWords.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 用marker展示输入提示list选中数据
     *
     * @param tip
     */
    private void addTipMarker(Tip tip) {
        if (tip == null) {
            return;
        }
        mPoiMarker = mAMap.addMarker(new MarkerOptions());
        LatLonPoint point = tip.getPoint();
        if (point != null) {
            LatLng markerPosition = new LatLng(point.getLatitude(), point.getLongitude());
            mPoiMarker.setPosition(markerPosition);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 17));
        }
        mPoiMarker.setTitle(tip.getName());
        mPoiMarker.setSnippet(tip.getAddress());
    }
}


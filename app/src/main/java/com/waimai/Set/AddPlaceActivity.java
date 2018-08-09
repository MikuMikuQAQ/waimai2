package com.waimai.Set;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.location.*;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.*;
import com.waimai.Set.Presenter.AddPlacePresenter;
import com.waimai.Set.Presenter.IAddPlacePresenter;
import com.waimai.Set.Presenter.ILocationListenner;
import com.waimai.Set.Presenter.LocationListenner;
import com.waimai.Set.View.IAddPlaceView;
import com.waimai.View.R;

import java.util.ArrayList;
import java.util.List;

public class AddPlaceActivity extends AppCompatActivity implements IAddPlaceView {

    private IAddPlacePresenter addPlacePresenter;

    private BaiduMap baiduMap;

    private LocationClient locationClient;

    private LocationClientOption option;

    private UiSettings settings;

    private ILocationListenner locationListenner;

    @BindView(R.id.baidu_map)
    MapView mapView;

    @BindViews({R.id.place_add_user, R.id.place_add_edit})
    List<AppCompatEditText> editTexts;

    @OnClick({R.id.place_add_return, R.id.place_save,R.id.place_add_gps})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.place_add_return:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.place_save:
                addPlacePresenter.savePlace(editTexts.get(0).getText().toString(),editTexts.get(1).getText().toString());
                break;
            case R.id.place_add_gps:
                baiduMap.clear();
                baiduMap.setMyLocationEnabled(true);
                locationClient.start();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        接受context参数
        locationClient = new LocationClient(getApplicationContext());
//        注册定位监听器
        locationClient.registerLocationListener(new LocationListenner(this));
//        初始化百度SDK
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_addplace);

        addPlacePresenter = new AddPlacePresenter(this,this);

        ButterKnife.bind(this);

        locationListenner = new LocationListenner(this);

        mapView.showZoomControls(false);//缩放按钮是否显示
        mapView.showScaleControl(false);//比例尺状态
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        settings = baiduMap.getUiSettings();
        settings.setRotateGesturesEnabled(true);

//        调用权限接口，声明权限
        addPlacePresenter.getPermission();

//        百度地图点击事件
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.clear();
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_gps);
                MarkerOptions options = new MarkerOptions().position(latLng).icon(bitmap);
                baiduMap.addOverlay(options);
                locationListenner.getPlace(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    @Override
    public void saveStatus(int num) {
        switch (num) {
            case 0:
                Toast.makeText(this, "请填写收货人", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "请填写收货地址", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

/*
    设置缩放级别
    设置定位
    */
    @Override
    public void setBaiduMap(MapStatusUpdate update) {
        baiduMap.animateMapStatus(update);
    }

/*

* 显示定位
*
* */
    @Override
    public void setBaiduMap(MyLocationData locationData) {
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    public void setLocation(String place) {
        editTexts.get(1).setText(place);
    }

    @Override
    public void getLocation(){
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        locationClient.setLocOption(option);
        locationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用！", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    getLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

}

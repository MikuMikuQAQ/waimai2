package com.waimai.Set.Presenter;

import android.os.Handler;
import android.util.Log;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.*;
import com.waimai.Set.View.IAddPlaceView;

public class LocationListenner extends BDAbstractLocationListener implements ILocationListenner {

    private IAddPlaceView addPlaceView;

    private boolean isFirstLocate = true;

    public LocationListenner(IAddPlaceView addPlaceView) {
        this.addPlaceView = addPlaceView;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
            navigateTo(bdLocation);
        }
        String place = bdLocation.getProvince()+" "+bdLocation.getCity()+" "+bdLocation.getDistrict()+" "+bdLocation.getStreet();
        addPlaceView.setLocation(place);
    }

    private void navigateTo(BDLocation location){
        if (isFirstLocate) {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            addPlaceView.setBaiduMap(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            addPlaceView.setBaiduMap(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData locationData = builder.build();
        addPlaceView.setBaiduMap(locationData);
    }

    @Override
    public void getPlace(LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                addPlaceView.setLocation(result.getAddress());
            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        //
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }
}

package com.waimai.Set.View;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MyLocationData;

public interface IAddPlaceView {
    void saveStatus(int num);
    void setBaiduMap(MapStatusUpdate update);
    void setBaiduMap(MyLocationData locationData);
    void setLocation(String place);
    void getLocation();
}

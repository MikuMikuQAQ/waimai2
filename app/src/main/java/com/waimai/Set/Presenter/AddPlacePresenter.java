package com.waimai.Set.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.waimai.Set.AddPlaceActivity;
import com.waimai.Set.Model.Waimai_Place;
import com.waimai.Set.View.IAddPlaceView;

import java.util.ArrayList;
import java.util.List;

public class AddPlacePresenter implements IAddPlacePresenter {

    private IAddPlaceView addPlaceView;

    private Context context;

    public AddPlacePresenter(IAddPlaceView addPlaceView, Context context) {
        this.addPlaceView = addPlaceView;
        this.context = context;
    }

    @Override
    public void savePlace(String name, String place) {
        if (!name.isEmpty()){
            if (!place.isEmpty()){
                Waimai_Place places = new Waimai_Place();
                places.setReceiptuser(name);
                places.setReceiptplace(place);
                if (places.save()){
                    addPlaceView.saveStatus(3);
                }else {
                    addPlaceView.saveStatus(2);
                }
            }else {
                addPlaceView.saveStatus(1);
            }
        }else {
            addPlaceView.saveStatus(0);
        }
    }

    @Override
    public void getPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions((AddPlaceActivity) context, permissions, 1);
        } else {
            addPlaceView.getLocation();
        }
    }
}

package com.waimai.Set.Presenter;

import android.widget.Toast;
import com.waimai.Set.Model.Waimai_Place;
import com.waimai.Set.View.IAddPlaceView;

public class AddPlacePresenter implements IAddPlacePresenter {

    private IAddPlaceView addPlaceView;

    public AddPlacePresenter(IAddPlaceView addPlaceView) {
        this.addPlaceView = addPlaceView;
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
}

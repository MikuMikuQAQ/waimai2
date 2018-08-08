package com.waimai.Set.Presenter;

import com.waimai.Set.Model.Waimai_Place;
import com.waimai.Set.View.IPlaceView;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PlacePresenter implements IPlacePresenter {

    private IPlaceView placeView;

    private List<Waimai_Place> placeList = new ArrayList<>();

    public PlacePresenter(IPlaceView placeView) {
        this.placeView = placeView;
    }

    @Override
    public void addPlaceList() {
        placeList = LitePal.findAll(Waimai_Place.class);
    }

    @Override
    public List<Waimai_Place> sendPlaceList() {
        List<Waimai_Place> placeList;
        placeList = this.placeList;
        return placeList;
    }
}

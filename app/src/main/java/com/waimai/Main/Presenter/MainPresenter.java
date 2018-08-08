package com.waimai.Main.Presenter;

import com.waimai.Main.Model.ShopList;
import com.waimai.Main.View.IMainView;
import com.waimai.View.R;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements IMainPresenter {

    private IMainView mainView;

    private List<ShopList> shopLists = new ArrayList<>();

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void addShopList() {
        ShopList shopList;
        for (int i = 1; i < 7; i++) {
            shopList = new ShopList(R.drawable.ic_food, "第" + i + "食堂", "月销 666", "学校食堂", "起送 ¥0", "每单送20积分");
            shopLists.add(shopList);
        }

    }

    @Override
    public List<ShopList> sendShopList() {
        List<ShopList> shopLists;
        shopLists = this.shopLists;
        return shopLists;
    }

}

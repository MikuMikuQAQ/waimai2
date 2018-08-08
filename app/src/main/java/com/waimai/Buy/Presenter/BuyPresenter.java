package com.waimai.Buy.Presenter;

import com.waimai.Buy.Model.MenuRight;
import com.waimai.Buy.View.IBuyView;
import com.waimai.View.R;

import java.util.ArrayList;
import java.util.List;

public class BuyPresenter implements IBuyPresenter {

    private IBuyView buyView;

    private List<MenuRight> rights = new ArrayList<>();

    public BuyPresenter(IBuyView buyView) {
        this.buyView = buyView;
    }

    @Override
    public void addRightList() {
        MenuRight right;
        right = new MenuRight(R.drawable.ic_food, "红烧肉", "12");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "红烧牛肉", "14");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "青椒小炒肉", "10");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "土豆小炒肉", "10");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "回锅肉", "10");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "盐煎肉", "10");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "鱼香肉丝", "9");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "泡椒炒肉", "9");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "藤藤菜", "6");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "土豆丝", "6");
        rights.add(right);
        right = new MenuRight(R.drawable.ic_food, "煮白菜", "6");
        rights.add(right);
    }

    @Override
    public List<MenuRight> sendMenu() {
        List<MenuRight> rights;
        rights = this.rights;
        return rights;
    }
}

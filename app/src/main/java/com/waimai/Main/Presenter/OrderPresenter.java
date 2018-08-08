package com.waimai.Main.Presenter;

import com.waimai.Main.Model.Waimai_Dingdan;
import com.waimai.Main.View.IOrderView;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class OrderPresenter implements IOrderPresenter {

    private IOrderView orderView;

    private List<Waimai_Dingdan> dingdanList = new ArrayList<>();

    public OrderPresenter(IOrderView orderView) {
        this.orderView = orderView;
    }

    @Override
    public void addOrderList() {
        dingdanList = LitePal.findAll(Waimai_Dingdan.class);
    }

    @Override
    public List<Waimai_Dingdan> sendOrderList() {
        List<Waimai_Dingdan> dingdanList;
        dingdanList = this.dingdanList;
        return dingdanList;
    }
}

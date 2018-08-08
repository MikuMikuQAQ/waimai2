package com.waimai.Pay.Presenter;

import android.content.ContentValues;
import com.waimai.Login.Model.Waimai_User;
import com.waimai.Main.Model.Waimai_Dingdan;
import com.waimai.Pay.View.IPaywaitView;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaywaitPresenter implements IPaywaitPresenter {

    private IPaywaitView paywaitView;

    private Double balance, money;
    private String shopName, dateTime, str;
    private int foodNum, mYear, mMonth, mDay,integral,id;
    private List<Waimai_User> userList = new ArrayList<>();

    public PaywaitPresenter(IPaywaitView paywaitView) {
        this.paywaitView = paywaitView;
    }

    @Override
    public void getDate() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateTime = mYear + "-" + (mMonth + 1) + "-" + mDay;
    }

    @Override
    public void yzMsg(String shopName,String foodName, Double money, int foodNum) {
        userList = LitePal.where("status = ?","1").find(Waimai_User.class);
        balance = userList.get(0).getBalance();
        integral = userList.get(0).getIntegral();
        id = userList.get(0).getId();
        getDate();
        if (balance > money) {
            Double i = balance - money;
            integral += 20;
            Waimai_Dingdan dingdan = new Waimai_Dingdan();
            dingdan.setBuyTime(dateTime);
            dingdan.setFoodMoney(money);
            dingdan.setFoodNum(foodNum);
            dingdan.setShop(shopName);
            dingdan.setFoodName(foodName);
            dingdan.setStatus(0);
            ContentValues values = new ContentValues();
            values.put("balance",i);
            values.put("integral",integral);
            LitePal.updateAll(Waimai_User.class,values,"id = ?",String.valueOf(id));
            if (dingdan.save()) {
                paywaitView.getStatus(2);
            } else {
                paywaitView.getStatus(1);
            }
        } else {
            paywaitView.getStatus(0);
        }

    }
}
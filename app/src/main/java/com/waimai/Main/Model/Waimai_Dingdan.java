package com.waimai.Main.Model;

import org.litepal.crud.LitePalSupport;

public class Waimai_Dingdan extends LitePalSupport {

    private int Id;

    private int status;

    private int imgId;

    private String shop;

    private double foodMoney;

    private String foodName;

    private int foodNum;

    private String buyTime;

    public Waimai_Dingdan() {
    }

    public Waimai_Dingdan(int id, int status, int imgId, String shop, double foodMoney, String foodName, int foodNum, String buyTime) {
        Id = id;
        this.status = status;
        this.imgId = imgId;
        this.shop = shop;
        this.foodMoney = foodMoney;
        this.foodName = foodName;
        this.foodNum = foodNum;
        this.buyTime = buyTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public double getFoodMoney() {
        return foodMoney;
    }

    public void setFoodMoney(double foodMoney) {
        this.foodMoney = foodMoney;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
}

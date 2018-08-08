package com.waimai.Buy.Model;

public class MenuRight {

    private int imgId;

    private String buyRightTitle;

    private String money;

    public MenuRight() {
    }

    public MenuRight(int imgId, String buyRightTitle, String money) {
        this.imgId = imgId;
        this.buyRightTitle = buyRightTitle;
        this.money = money;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getBuyRightTitle() {
        return buyRightTitle;
    }

    public void setBuyRightTitle(String buyRightTitle) {
        this.buyRightTitle = buyRightTitle;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

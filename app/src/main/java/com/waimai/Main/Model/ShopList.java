package com.waimai.Main.Model;

public class ShopList {

    private int imgId;
    private String shopName;
    private String salesNum;
    private String category;
    private String condition;
    private String offer;

    public ShopList() {
    }

    public ShopList(int imgId, String shopName, String salesNum, String category, String condition, String offer) {
        this.imgId = imgId;
        this.shopName = shopName;
        this.salesNum = salesNum;
        this.category = category;
        this.condition = condition;
        this.offer = offer;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(String salesNum) {
        this.salesNum = salesNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}

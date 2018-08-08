package com.waimai.Set.Model;

import org.litepal.crud.LitePalSupport;

public class Waimai_Place extends LitePalSupport {

    private int Id;

    private String receiptuser;

    private String receiptplace;

    public Waimai_Place() {
    }

    public Waimai_Place(int id, String receiptuser, String receiptplace) {
        Id = id;
        this.receiptuser = receiptuser;
        this.receiptplace = receiptplace;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReceiptuser() {
        return receiptuser;
    }

    public void setReceiptuser(String receiptuser) {
        this.receiptuser = receiptuser;
    }

    public String getReceiptplace() {
        return receiptplace;
    }

    public void setReceiptplace(String receiptplace) {
        this.receiptplace = receiptplace;
    }
}

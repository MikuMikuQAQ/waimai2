package com.waimai.Contact.Model;

public class WaimaiContact {

    private String contactName;
    private String contactNum;

    public WaimaiContact(String contactName, String contactNum) {
        this.contactName = contactName;
        this.contactNum = contactNum;
    }

    public WaimaiContact() {
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
}

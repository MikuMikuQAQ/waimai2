package com.waimai.Contact.Presenter;

import com.waimai.Contact.Model.WaimaiContact;

import java.util.List;

public interface IContactPresenter {
    void getPermission();
    void readContact();
    List<WaimaiContact> sendContact();
}

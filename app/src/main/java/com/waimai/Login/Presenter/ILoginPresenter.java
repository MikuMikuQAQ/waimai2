package com.waimai.Login.Presenter;

public interface ILoginPresenter {
    void verifyLogin(String user,String password);
    boolean checkLogin();
}

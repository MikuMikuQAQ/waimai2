package com.waimai.Login.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.waimai.Login.Model.Waimai_User;
import com.waimai.Login.View.ILoginView;
import com.waimai.Login.LoginActivity;
import org.litepal.LitePal;

import java.util.List;

public class LoginPresenter implements ILoginPresenter {

    private Context context;

    private ILoginView loginView;

    public LoginPresenter(LoginActivity loginActivity, ILoginView loginView) {
        context = loginActivity;
        this.loginView = loginView;
    }

    @Override
    public void verifyLogin(String user, String password) {
        if (!user.isEmpty() || !password.isEmpty()) {
            if (!LitePal.findAll(Waimai_User.class).isEmpty()) {
                List<Waimai_User> waimaiUsers = LitePal.select("password").where("username = ?", user).find(Waimai_User.class);
                if (waimaiUsers.size() == 0) {
                    loginView.getVerifyMsg(false, 2);
                } else {
                    if (password.equals(waimaiUsers.get(0).getPassword())) {
                        ContentValues values = new ContentValues();
                        values.put("status",true);
                        LitePal.updateAll(Waimai_User.class,values,"username = ?", user);
                        loginView.getVerifyMsg(true, 4);
                    } else {
                        loginView.getVerifyMsg(false, 3);
                    }
                }
            } else {
                loginView.getVerifyMsg(false, 1);
            }
        } else {
            loginView.getVerifyMsg(false, 0);
        }
    }

    @Override
    public boolean checkLogin() {
        List<Waimai_User> users = LitePal.where("status = ?","1").find(Waimai_User.class);
        if (users.size() != 0){
            return true;
        }
        return false;
    }

}

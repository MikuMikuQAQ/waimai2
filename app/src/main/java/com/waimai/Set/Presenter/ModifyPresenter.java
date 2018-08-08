package com.waimai.Set.Presenter;

import android.content.ContentValues;
import android.util.Log;
import com.waimai.Login.Model.Waimai_User;
import com.waimai.Set.View.IModifyView;
import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyPresenter implements IModifyPresenter {

    private String yzPassword = "^[A-Za-z0-9]{6,20}$";
    private Pattern patternPassword = Pattern.compile(yzPassword);;
    private Matcher matcherPassword;

    private IModifyView modifyView;

    public ModifyPresenter(IModifyView modifyView) {
        this.modifyView = modifyView;
    }

    @Override
    public void updatePwd(String userName,String oldPwd,String newPwd,String newAgainPwd) {
        matcherPassword = patternPassword.matcher(newPwd);
        if (matcherPassword.matches()){
            if (newPwd.equals(newAgainPwd)){
                List<Waimai_User> users = LitePal.select("password").where("username = ?",userName).find(Waimai_User.class);
                if (users.get(0).getPassword().equals(oldPwd)){
                    ContentValues values = new ContentValues();
                    values.put("password",newPwd);
                    LitePal.updateAll(Waimai_User.class,values,"username = ?",userName);
                    modifyView.getVerifyMsg(true,3);
                }else {
                    modifyView.getVerifyMsg(false,2);
                }
            }else {
                modifyView.getVerifyMsg(false,0);
            }
        } else {
            modifyView.getVerifyMsg(false,1);
        }
    }
}

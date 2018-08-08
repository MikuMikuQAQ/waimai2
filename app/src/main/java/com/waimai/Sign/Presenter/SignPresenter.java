package com.waimai.Sign.Presenter;

import android.content.Context;
import com.waimai.Login.Model.Waimai_User;
import com.waimai.Sign.View.ISignView;
import com.waimai.Sign.SignActivity;
import org.litepal.LitePal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignPresenter implements ISignPresenter {

    private Context context;

    private ISignView signView;

    private Pattern patternUser,patternPassword;

    private Matcher matcherUser,matcherPassword;

    private String yzUser = "^[A-Za-z0-9_]{4,20}$";

    private String yzPassword = "^[A-Za-z0-9]{6,20}$";

    public SignPresenter(SignActivity signActivity, ISignView signView){
        context = signActivity;
        this.signView = signView;
    }

    @Override
    public void doSign(String user, String password, String yesPassword) {
        patternUser = Pattern.compile(yzUser);
        patternPassword = Pattern.compile(yzPassword);
        matcherUser = patternUser.matcher(user);
        matcherPassword = patternPassword.matcher(password);
        if (matcherUser.matches()) {
            if (matcherPassword.matches()) {
                if (password.equals(yesPassword)) {
                    if (LitePal.findAll(Waimai_User.class).isEmpty()) {
                        Waimai_User waimaiUser = new Waimai_User();
                        waimaiUser.setUsername(user);
                        waimaiUser.setPassword(password);
                        waimaiUser.setBalance(0);
                        waimaiUser.setIntegral(0);
                        waimaiUser.setStatus(true);
                        waimaiUser.save();
                        signView.getSignMsg(waimaiUser.save(),4);
                    } else {
                        signView.getSignMsg(false,3);
                    }
                } else {
                    signView.getSignMsg(false,2);
                }
            } else {
                signView.getSignMsg(false,1);
            }
        } else {
            signView.getSignMsg(false,0);
        }
    }
}

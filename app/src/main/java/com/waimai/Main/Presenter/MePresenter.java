package com.waimai.Main.Presenter;

import android.os.Handler;
import com.waimai.Main.Model.Set;
import com.waimai.Main.View.IMeView;
import com.waimai.View.R;

import java.util.ArrayList;
import java.util.List;

public class MePresenter implements IMePresenter {

    private IMeView meView;

    private List<Set> setList = new ArrayList<>();

    public MePresenter(IMeView meView) {
        this.meView = meView;
    }

    @Override
    public void addSetList() {
        Set pwd = new Set("修改密码", R.drawable.cog_solid);
        setList.add(pwd);
        Set place = new Set("送餐地址", R.drawable.ic_gps_locate);
        setList.add(place);
        Set logout = new Set("退出账户", R.drawable.unlink);
        setList.add(logout);
    }

    @Override
    public List<Set> sendSetList() {
        List<Set> setList;
        setList = this.setList;
        return setList;
    }
}

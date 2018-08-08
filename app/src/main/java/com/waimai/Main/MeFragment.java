package com.waimai.Main;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.*;
import com.waimai.Login.Model.Waimai_User;
import com.waimai.Main.Adapter.SetRecyclerAdapter;
import com.waimai.Main.Model.Set;
import com.waimai.Main.Presenter.IMePresenter;
import com.waimai.Main.Presenter.MainPresenter;
import com.waimai.Main.Presenter.MePresenter;
import com.waimai.Main.View.IMeView;
import com.waimai.View.R;
import org.litepal.LitePal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment implements IMeView {

    private View view;

    private List<Set> setList = new ArrayList<>();
    
    protected WeakReference<View> mRootView;

    private Unbinder unbinder;

    private SetRecyclerAdapter adapter;

    private IMePresenter mePresenter;

    private Double balance;

    private Double js;

    private String userName;

    private int integral;

    private List<Waimai_User> users = new ArrayList<>();

    @BindView(R.id.setlist)
    RecyclerView recyclerView;

    @BindViews({R.id.me_username,R.id.meony_text,R.id.integral_text})
    List<AppCompatTextView> viewList;

    @BindViews({R.id.balance_layout,R.id.integral_layout})
    List<RelativeLayout> layoutList;

    @OnClick({R.id.balance_layout,R.id.integral_layout})
    public void onClicked(View view){
        switch (view.getId()) {
            case R.id.balance_layout:
                users = LitePal.select("username","balance","integral").find(Waimai_User.class);
                balance = Double.valueOf(users.get(0).getBalance());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final String[] strings = {"100", "50", "30"};
                builder.setTitle("在线充值,请选择充值金额").setSingleChoiceItems(strings, 3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                js = 0.0;
                                js = balance + 100;
                                break;
                            case 1:
                                js = 0.0;
                                js = balance + 50;
                                break;
                            case 2:
                                js = 0.0;
                                js = balance + 30;
                                break;
                            default:
                                break;
                        }
                    }
                }).setPositiveButton("充值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues values = new ContentValues();
                        values.put("balance",js);
                        LitePal.update(Waimai_User.class,values,1);
                        viewList.get(1).setText(js.toString());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        js = null;
                    }
                }).show();
                break;
            case R.id.integral_layout:
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.fragment_me, null);
            unbinder = ButterKnife.bind(this, view);
            mePresenter = new MePresenter(this);
            mRootView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users = LitePal.select("username","balance","integral").find(Waimai_User.class);

        userName = users.get(0).getUsername().toString();
        balance = Double.valueOf(users.get(0).getBalance());
        integral = Integer.valueOf(users.get(0).getIntegral());

        viewList.get(0).setText(userName);
        viewList.get(1).setText(balance.toString());
        viewList.get(2).setText(String.valueOf(integral));

        addSetList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SetRecyclerAdapter(this.getActivity(),this, setList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    
    private void addSetList(){
        mePresenter.addSetList();
        setList = mePresenter.sendSetList();
    }

    @Override
    public String sendName(){
        return userName;
    }
}

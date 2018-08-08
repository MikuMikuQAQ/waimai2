package com.waimai.Main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.waimai.Main.Adapter.ShopRecyclerAdapter;
import com.waimai.Main.Model.ShopList;
import com.waimai.Main.Presenter.IMainPresenter;
import com.waimai.Main.Presenter.MainPresenter;
import com.waimai.Main.View.IMainView;
import com.waimai.View.R;
import org.litepal.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements IMainView, SwipeRefreshLayout.OnRefreshListener {

    private View view;

    protected WeakReference<View> mRootView;

    private List<ShopList> shopLists = new ArrayList<>();

    private IMainPresenter mainPresenter;

    private Unbinder unbinder;

    private ShopRecyclerAdapter adapter;

    @BindView(R.id.main_list)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_main_list)
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (view == null) {
//            view = inflater.inflate(R.layout.fragment_main,container,false);
//            ButterKnife.bind(this,view);
//        }
//        ViewGroup group = (ViewGroup) view.getParent();
//        if (group != null) group.removeView(view);
//        return view;
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.fragment_main, null);
            unbinder = ButterKnife.bind(this, view);
            mainPresenter = new MainPresenter(this);
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

        addShopList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShopRecyclerAdapter(this.getActivity(), shopLists);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shopLists.clear();
                mainPresenter.addShopList();
                shopLists = mainPresenter.sendShopList();
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    private void addShopList(){
        mainPresenter.addShopList();
        shopLists = mainPresenter.sendShopList();
    }
}

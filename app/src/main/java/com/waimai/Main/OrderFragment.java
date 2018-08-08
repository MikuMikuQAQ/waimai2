package com.waimai.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.waimai.Main.Adapter.OrderAdapter;
import com.waimai.Main.Model.Waimai_Dingdan;
import com.waimai.Main.Presenter.IOrderPresenter;
import com.waimai.Main.Presenter.MePresenter;
import com.waimai.Main.Presenter.OrderPresenter;
import com.waimai.Main.View.IOrderView;
import com.waimai.View.R;
import org.litepal.LitePal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class OrderFragment extends Fragment implements IOrderView, SwipeRefreshLayout.OnRefreshListener {

    protected WeakReference<View> mRootView;

    private Unbinder unbinder;

    private View view;

    private IOrderPresenter orderPresenter;

    private OrderAdapter adapter;

    private List<Waimai_Dingdan> dingdans = new ArrayList<>();

    private List<Waimai_Dingdan> dingdans1 = new ArrayList<>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dingdans1 = LitePal.findAll(Waimai_Dingdan.class);
                    if (dingdans.size() < dingdans1.size()) {
                        for (int i = dingdans.size(); i < dingdans1.size();i++) {
                            Waimai_Dingdan dingdan = dingdans1.get(i);
                            dingdans.add(dingdan);
                        }
                        adapter.notifyItemInserted(dingdans.size());
                    }
                    if (refreshLayout != null){
                        refreshLayout.setRefreshing(false);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @BindView(R.id.order_list)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_list)
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.fragment_order, null);
            unbinder = ButterKnife.bind(this, view);
            mRootView = new WeakReference<View>(view);
            orderPresenter = new OrderPresenter(this);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addOrderList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrderAdapter(this.getActivity(),dingdans);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshLayout.setRefreshing(false);
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0,1500);
    }

    private void addOrderList(){
        orderPresenter.addOrderList();
        dingdans = orderPresenter.sendOrderList();
        handler.sendEmptyMessageDelayed(0,1500);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                refreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0,1500);
            }
        }
    }
}

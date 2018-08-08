package com.waimai.Set;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Set.Adapter.PlaceAdapter;
import com.waimai.Set.Model.Waimai_Place;
import com.waimai.Set.Presenter.IPlacePresenter;
import com.waimai.Set.Presenter.PlacePresenter;
import com.waimai.Set.View.IPlaceView;
import com.waimai.View.R;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends AppCompatActivity implements IPlaceView, SwipeRefreshLayout.OnRefreshListener {

    private PlaceAdapter adapter;
    private List<Waimai_Place> places = new ArrayList<>();
    private List<Waimai_Place> places1 = new ArrayList<>();
    private IPlacePresenter placePresenter;

    @BindView(R.id.refresh_placelist)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.place_recycler)
    RecyclerView recyclerView;

    @OnClick({R.id.place_return, R.id.place_add})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.place_return:
                finish();
                break;
            case R.id.place_add:
                Intent intent = new Intent(this, AddPlaceActivity.class);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placePresenter = new PlacePresenter(this);

        ButterKnife.bind(this);

        addPlaceList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(PlaceActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(PlaceActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PlaceAdapter(this, this, places);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
    }

    private void addPlaceList() {
        placePresenter.addPlaceList();
        places = placePresenter.sendPlaceList();
        handler.sendEmptyMessageDelayed(0, 1500);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    places1 = LitePal.findAll(Waimai_Place.class);
                    if (places.size() < places1.size()) {

                        for (int i = places.size(); i < places1.size(); i++) {
                            Waimai_Place waimai_place = places1.get(i);
                            places.add(waimai_place);
                            //Log.e("TAG", "run: "+places.get(0).getReceiptuser());
                        }
                        adapter.notifyItemInserted(places.size());
                    }
                    refreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                refreshLayout.setRefreshing(true);

                handler.sendEmptyMessageDelayed(0, 1500);
            }
        }
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 1500);
    }
}

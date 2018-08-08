package com.waimai.Buy;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Buy.Adapter.RightRecyclerAdapter;
import com.waimai.Buy.Model.MenuRight;
import com.waimai.Buy.Presenter.BuyPresenter;
import com.waimai.Buy.Presenter.IBuyPresenter;
import com.waimai.Buy.View.IBuyView;
import com.waimai.Pay.PaymentActivity;
import com.waimai.View.R;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity implements IBuyView {

    private Intent getDeta,sendMsg;

    private String str;

    private LinearLayoutManager layoutManager;

    private List<MenuRight> rights = new ArrayList<>();

    private RightRecyclerAdapter rightAdapter;

    private IBuyPresenter buyPresenter;

    @BindViews({R.id.buy_shop_name,R.id.buy_sum})
    List<AppCompatTextView> textViews;

    @BindViews({R.id.buy_return,R.id.buy_shop_img})
    List<AppCompatImageView> imageViews;

    @BindView(R.id.menu_right)
    RecyclerView recyclerView;

    @BindView(R.id.buy_button)
    AppCompatButton buyButton;

    @OnClick({R.id.buy_return,R.id.buy_button})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_return:
                finish();
                break;
            case R.id.buy_button:
                if (rightAdapter.getJiSuan() <= 0){
                    Toast.makeText(this,"请选择菜品",Toast.LENGTH_SHORT).show();
                }else {
                    sendMsg = new Intent(this, PaymentActivity.class);
                    sendMsg.putExtra("Money",String.valueOf(rightAdapter.getJiSuan()));
                    sendMsg.putExtra("shopName",str);
                    sendMsg.putExtra("foodNum",String.valueOf(rightAdapter.getfoodCount()));
                    sendMsg.putExtra("foodName",rightAdapter.getFoodName());
                    startActivity(sendMsg);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        buyPresenter = new BuyPresenter(this);

        ButterKnife.bind(this);

        getDeta = getIntent();
        str = getIntent().getStringExtra("shopName");

        textViews.get(0).setText(str);

        addRights();

        layoutManager = new LinearLayoutManager(BuyActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(BuyActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rightAdapter = new RightRecyclerAdapter(BuyActivity.this, rights);
        recyclerView.setAdapter(rightAdapter);
    }

    private void addRights(){
        buyPresenter.addRightList();
        rights = buyPresenter.sendMenu();
    }

    @Override
    public void updateUI(Double sum) {
        if (sum == 0){
            textViews.get(1).setText("请下单");
        }else {
            textViews.get(1).setText("¥"+sum);
        }
    }
}

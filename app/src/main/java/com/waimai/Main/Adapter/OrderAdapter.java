package com.waimai.Main.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.waimai.Main.Model.Waimai_Dingdan;
import com.waimai.View.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;

    private List<Waimai_Dingdan> dingdanList;

    public OrderAdapter(Context context, List<Waimai_Dingdan> dingdanList) {
        this.context = context;
        this.dingdanList = dingdanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Waimai_Dingdan dingdan = dingdanList.get(i);
        //Log.e("TAG", "onBindViewHolder: " + new Gson().toJson(viewHolder.textViews.get(0)) );
        if (dingdan.getImgId() == 0) {
            viewHolder.orderImg.setImageResource(R.drawable.ic_food);
        } else {
            viewHolder.orderImg.setImageResource(dingdan.getImgId());
        }
        viewHolder.textViews.get(0).setText(dingdan.getShop());
        viewHolder.textViews.get(1).setText(dingdan.getBuyTime());
        viewHolder.textViews.get(2).setText(dingdan.getFoodName());
        if (dingdan.getStatus() == 0){
            viewHolder.textViews.get(3).setText("未送达");
        } else if (dingdan.getStatus() == 1) {
            viewHolder.textViews.get(3).setText("已送达");
        }
        viewHolder.textViews.get(4).setText(String.valueOf(dingdan.getFoodMoney()));
        viewHolder.textViews.get(5).setText(String.valueOf(dingdan.getFoodNum()));
    }

    @Override
    public int getItemCount() {
        return dingdanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View orderView;

        @BindViews({R.id.shop_dingdan_name,R.id.sales_num,R.id.food_name,R.id.status,R.id.dingdan_money,R.id.food_num})
        List<TextView> textViews;

        @BindView(R.id.shop_img)
        ImageView orderImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}

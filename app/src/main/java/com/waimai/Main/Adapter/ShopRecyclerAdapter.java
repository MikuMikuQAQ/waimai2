package com.waimai.Main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.waimai.Buy.BuyActivity;
import com.waimai.Main.Model.ShopList;
import com.waimai.View.R;

import java.util.List;

public class ShopRecyclerAdapter extends RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ShopList> mShopList;

    public ShopRecyclerAdapter(Context context, List<ShopList> shopList) {
        this.context = context;
        mShopList = shopList;
    }

    
    @Override
    public ShopRecyclerAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BuyActivity.class);
                String str = holder.textViewList.get(0).getText().toString();
                intent.putExtra("shopName",str);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ShopList list = mShopList.get(i);
        viewHolder.imgId.setImageResource(list.getImgId());
        viewHolder.textViewList.get(0).setText(list.getShopName());
        viewHolder.textViewList.get(1).setText(list.getSalesNum());
        viewHolder.textViewList.get(2).setText(list.getCategory());
        viewHolder.textViewList.get(3).setText(list.getCondition());
        viewHolder.textViewList.get(4).setText(list.getOffer());
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View shopView;

        @BindView(R.id.shop_main_img)
        ImageView imgId;

        @BindViews({R.id.shop_main_name,
                R.id.sales_num,
                R.id.category,
                R.id.condition,
                R.id.offer})
        List<TextView> textViewList;

        public ViewHolder( View itemView) {
            super(itemView);
            shopView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.waimai.Buy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.waimai.Buy.BuyActivity;
import com.waimai.Buy.Model.MenuRight;
import com.waimai.Buy.View.IBuyView;
import com.waimai.View.R;

import java.util.List;

public class RightRecyclerAdapter extends RecyclerView.Adapter<RightRecyclerAdapter.ViewHolder> implements IRightRecyclerAdapter {

    private IBuyView buyView;

    private List<MenuRight> rights;

    public double sum = 0;

    private int count = 0;

    private BuyActivity buyActivity;

    private String foodName;

    public RightRecyclerAdapter(IBuyView buyView, List<MenuRight> rights) {
        this.buyView = buyView;
        this.rights = rights;
    }

    @NonNull
    @Override
    public RightRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.right_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.imageViews.get(1).setOnClickListener(new View.OnClickListener() {
            int sum = 0;
            @Override
            public void onClick(View view) {
                sum = Integer.parseInt(holder.textViews.get(2).getText().toString());
                sum++;
                if (foodName == null){
                    foodName = holder.textViews.get(0).getText().toString();
                }
                holder.textViews.get(2).setText(String.valueOf(sum));
                double food = Double.parseDouble(holder.textViews.get(1).getText().toString());
                foodCount(0);
                jiSuan(food, 0);
            }
        });
        holder.imageViews.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(holder.textViews.get(2).getText().toString());
                if (sum > 0) {
                    sum--;
                    if (sum == 0){
                        foodName = "";
                    }
                    holder.textViews.get(2).setText(String.valueOf(sum));
                    double food = Double.parseDouble(holder.textViews.get(1).getText().toString());
                    foodCount(1);
                    jiSuan(food, 1);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RightRecyclerAdapter.ViewHolder viewHolder, int i) {
        MenuRight right = rights.get(i);
        viewHolder.imageViews.get(0).setImageResource(right.getImgId());
        viewHolder.textViews.get(0).setText(right.getBuyRightTitle());
        viewHolder.textViews.get(1).setText(right.getMoney());
    }

    @Override
    public int getItemCount() {
        return rights.size();
    }

    @Override
    public void jiSuan(double js, int jc) {
        switch (jc) {
            case 0:
                sum = js + sum;
                buyView.updateUI(sum);
                break;
            case 1:
                if (sum >= js) {
                    sum = sum - js;
                    buyView.updateUI(sum);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void foodCount(int count) {
        switch (count) {
            case 0:
                this.count += 1;
                break;
            case 1:
                if (this.count <= 0) break;
                else {
                    this.count -= 1;
                    break;
                }
            default:
                break;
        }
    }

    @Override
    public int getfoodCount() {
        return count;
    }

    @Override
    public double getJiSuan() {
        return sum;
    }

    @Override
    public String getFoodName() {
        return foodName;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View rightView;

        @BindViews({R.id.menu_img,R.id.menu_add,R.id.menu_less})
        List<AppCompatImageView> imageViews;

        @BindViews({R.id.menu_food_name,R.id.menu_money,R.id.menu_num})
        List<AppCompatTextView> textViews;

        public ViewHolder(View itemView) {
            super(itemView);
            rightView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}

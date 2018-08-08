package com.waimai.Set.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
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
import com.waimai.Set.Model.Waimai_Place;
import com.waimai.Set.View.IPlaceView;
import com.waimai.View.R;
import org.litepal.LitePal;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context context;
    private IPlaceView placeView;
    private List<Waimai_Place> places;

    public PlaceAdapter(Context context, IPlaceView placeView, List<Waimai_Place> places) {
        this.context = context;
        this.placeView = placeView;
        this.places = places;
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Waimai_Place waimai_place = places.get(position);
                LitePal.deleteAll(Waimai_Place.class, "id =?" , String.valueOf(waimai_place.getId()));
                places.remove(position);
                notifyItemRemoved(position);
                Log.e("TAG", "onClick: "+ new Gson().toJson(places));
                Log.e("TAG", "onClick: "+ new Gson().toJson(LitePal.findAll(Waimai_Place.class)));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder viewHolder, int i) {
        Waimai_Place place = places.get(i);
        viewHolder.textViews.get(0).setText(place.getReceiptuser());
        viewHolder.textViews.get(1).setText(place.getReceiptplace());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View placeView;

        @BindView(R.id.trash_img)
        AppCompatImageView imageView;

        @BindViews({R.id.user_name,R.id.user_place})
        List<TextView> textViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }

}

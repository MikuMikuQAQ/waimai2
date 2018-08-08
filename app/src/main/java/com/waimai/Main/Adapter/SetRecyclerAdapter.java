package com.waimai.Main.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.waimai.Login.LoginActivity;
import com.waimai.Login.Model.Waimai_User;
import com.waimai.Main.MainActivity;
import com.waimai.Main.MeFragment;
import com.waimai.Main.Model.Set;
import com.waimai.Main.View.IMeView;
import com.waimai.Set.ModifyPwdActivity;
import com.waimai.Set.PlaceActivity;
import com.waimai.View.R;
import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

public class SetRecyclerAdapter extends RecyclerView.Adapter<SetRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<Set> setList;

    private IMeView meView;

    public SetRecyclerAdapter(Context context,IMeView meView, List<Set> setList) {
        this.context = context;
        this.setList = setList;
        this.meView = meView;
    }

    @NonNull
    @Override
    public SetRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.setView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                int position = holder.getAdapterPosition();
                Set set = setList.get(position);
                switch (position){
                    case 0:
                        intent = new Intent(context, ModifyPwdActivity.class);
                        String str = meView.sendName();
                        intent.putExtra("userName",str);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, PlaceActivity.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        if (MainActivity.class.isInstance(context)) {
                            ContentValues values = new ContentValues();
                            values.put("status",false);
                            LitePal.updateAll(Waimai_User.class,values);
                            MainActivity activity = (MainActivity) context;
                            intent = new Intent(context,LoginActivity.class);
                            context.startActivity(intent);
                            activity.finish();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SetRecyclerAdapter.ViewHolder viewHolder, int i) {
        Set set = setList.get(i);
        viewHolder.imageView.setImageResource(set.getImgId());
        viewHolder.textView.setText(set.getName());
    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View setView;

        @BindView(R.id.set_img)
        ImageView imageView;

        @BindView(R.id.set_name)
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}

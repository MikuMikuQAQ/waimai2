package com.waimai.Contact.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.waimai.Contact.ContactActivity;
import com.waimai.Contact.Model.WaimaiContact;
import com.waimai.View.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;

    private List<WaimaiContact> contacts;

    public ContactAdapter(Context context, List<WaimaiContact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactName = holder.textViews.get(0).getText().toString();
                String contactNum = holder.textViews.get(1).getText().toString();
                Intent intent = new Intent();
                intent.putExtra("contactName",contactName);
                intent.putExtra("contactNum",contactNum);
                ContactActivity activity = (ContactActivity) context;
                activity.setResult(RESULT_OK,intent);
                activity.finish();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        WaimaiContact contact = contacts.get(i);
        viewHolder.textViews.get(0).setText(contact.getContactName());
        viewHolder.textViews.get(1).setText(contact.getContactNum());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View contactView;

        @BindViews({R.id.contact_name,R.id.contact_num})
        List<TextView> textViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.waimai.Contact;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.waimai.Contact.Adapter.ContactAdapter;
import com.waimai.Contact.Model.WaimaiContact;
import com.waimai.Contact.Presenter.ContactPresenter;
import com.waimai.Contact.Presenter.IContactPresenter;
import com.waimai.Contact.View.IContactView;
import com.waimai.View.R;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements IContactView {

    private ActionBar actionBar;

    private ContactAdapter adapter;

    private List<WaimaiContact> contacts = new ArrayList<>();

    private IContactPresenter contactPresenter;

    @BindView(R.id.contact_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.contact_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);

        contactPresenter = new ContactPresenter(this,this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_icon_return_left_01);
        }

        contactPresenter.getPermission();

        contacts = contactPresenter.sendContact();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ContactAdapter(this,contacts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactPresenter.readContact();
                }
                break;
            default:
                break;
        }
    }
}

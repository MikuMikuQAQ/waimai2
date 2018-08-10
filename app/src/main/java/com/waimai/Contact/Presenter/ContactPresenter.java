package com.waimai.Contact.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.waimai.Contact.ContactActivity;
import com.waimai.Contact.Model.WaimaiContact;
import com.waimai.Contact.View.IContactView;

import java.util.ArrayList;
import java.util.List;

public class ContactPresenter implements IContactPresenter {

    private Context context;
    private IContactView contactView;
    private List<WaimaiContact> contactList = new ArrayList<>();

    public ContactPresenter(Context context, IContactView contactView) {
        this.context = context;
        this.contactView = contactView;
    }

    @Override
    public void getPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((ContactActivity) context,new String[]{Manifest.permission.READ_CONTACTS},1);
        } else {
            readContact();
        }
    }

    @Override
    public void readContact() {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor != null) {
                WaimaiContact contact;
                while (cursor.moveToNext()) {
                    String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contactNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactList.add(new WaimaiContact(contactName,contactNum));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public List<WaimaiContact> sendContact() {
        return contactList;
    }
}

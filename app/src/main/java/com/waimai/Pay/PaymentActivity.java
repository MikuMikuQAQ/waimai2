package com.waimai.Pay;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.View.R;

public class PaymentActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private FragmentTransaction transaction;

    private PaywaitFragment paywaitFragment;

    private PayokFragment payokFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        paywaitFragment = new PaywaitFragment();
        transaction.replace(R.id.payment_layout,paywaitFragment);

        transaction.commit();
    }
}

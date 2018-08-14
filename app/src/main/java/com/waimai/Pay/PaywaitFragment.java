package com.waimai.Pay;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.waimai.Main.MainActivity;
import com.waimai.Main.OrderFragment;
import com.waimai.Pay.Presenter.IPaywaitPresenter;
import com.waimai.Pay.Presenter.PaywaitPresenter;
import com.waimai.Pay.View.IPaywaitView;
import com.waimai.View.R;

import java.lang.ref.WeakReference;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class PaywaitFragment extends Fragment implements IPaywaitView {

    private FragmentManager fragmentManager;

    private FragmentTransaction transaction;

    private PayokFragment payokFragment;

    protected WeakReference<View> mRootView;

    private String shopName, str, foodName;

    private Unbinder unbinder;

    private int foodNum;

    private Double money;

    private Context context;

    private IPaywaitPresenter paywaitPresenter;

    @BindView(R.id.paywait_money)
    AppCompatTextView textView;

    @OnClick({R.id.paywait_button, R.id.paywait_return})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.paywait_button:
                getNotification();
                paywaitPresenter.yzMsg(shopName, foodName, money, foodNum);
                break;
            case R.id.paywait_return:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.fragment_paywait, null);
            unbinder = ButterKnife.bind(this, view);
            paywaitPresenter = new PaywaitPresenter(this);
            mRootView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        str = intent.getStringExtra("Money");
        shopName = intent.getStringExtra("shopName");
        foodNum = Integer.parseInt(intent.getStringExtra("foodNum"));
        foodName = intent.getStringExtra("foodName");
        money = Double.parseDouble(str);

        textView.setText(str);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getStatus(int num) {
        switch (num) {
            case 0:
                alertDialogSend("支付失败", "余额不足，请充值！");
                break;
            case 1:
                alertDialogSend("支付失败", "支付错误");
                break;
            default:
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                payokFragment = new PayokFragment();
                transaction.replace(R.id.payment_layout, payokFragment);
                transaction.commit();
                break;
        }
    }

    private void alertDialogSend(String str, String str1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(str).setMessage(str1).setPositiveButton(" 确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        }).show();
    }

    private void getNotification() {
       /* Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/

        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notificationCompat = new NotificationCompat.Builder(getContext())
                .setContentTitle("订单支付成功")
                .setContentText("在"+shopName+"购买了"+foodName+"等共计"+foodNum+"个商品，消费"+str+"元")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                //.setContentIntent(pendingIntent)
                .build();
        manager.notify(1,notificationCompat);
    }
}

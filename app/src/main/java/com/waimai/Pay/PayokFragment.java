package com.waimai.Pay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.waimai.Pay.Presenter.PaywaitPresenter;
import com.waimai.View.R;

import java.lang.ref.WeakReference;

public class PayokFragment extends Fragment {

    protected WeakReference<View> mRootView;

    private Unbinder unbinder;

    @OnClick({R.id.payok_button})
    public void onClicked(View view){
        switch (view.getId()){
            case R.id.payok_button:
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
            View view = inflater.inflate(R.layout.fragment_payok, null);
            unbinder = ButterKnife.bind(this, view);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

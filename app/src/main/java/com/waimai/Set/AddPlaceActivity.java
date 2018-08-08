package com.waimai.Set;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.Toast;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Set.Presenter.AddPlacePresenter;
import com.waimai.Set.Presenter.IAddPlacePresenter;
import com.waimai.Set.View.IAddPlaceView;
import com.waimai.View.R;

import java.util.List;

public class AddPlaceActivity extends AppCompatActivity implements IAddPlaceView {

    private IAddPlacePresenter addPlacePresenter;

    @BindViews({R.id.place_add_user, R.id.place_add_edit})
    List<AppCompatEditText> editTexts;

    @OnClick({R.id.place_add_return, R.id.place_save})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.place_add_return:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.place_save:
                addPlacePresenter.savePlace(editTexts.get(0).getText().toString(),editTexts.get(1).getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);

        addPlacePresenter = new AddPlacePresenter(this);

        ButterKnife.bind(this);
    }

    @Override
    public void saveStatus(int num) {
        switch (num) {
            case 0:
                Toast.makeText(this, "请填写收货人", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "请填写收货地址", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

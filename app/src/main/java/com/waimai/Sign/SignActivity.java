package com.waimai.Sign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Main.MainActivity;
import com.waimai.Sign.Presenter.ISignPresenter;
import com.waimai.Sign.Presenter.SignPresenter;
import com.waimai.Sign.View.ISignView;
import com.waimai.View.R;

import java.util.List;

public class SignActivity extends AppCompatActivity implements ISignView {

    private ActionBar actionBar;

    @BindViews({R.id.register_editText1,R.id.register_editText2,R.id.register_editText3})
    List<AppCompatEditText> editTexts;

    @BindView(R.id.register_toolbar)
    Toolbar toolbar;

    @OnClick({R.id.register_button})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.register_button:
                signPresenter.doSign(editTexts.get(0).getText().toString(),
                        editTexts.get(1).getText().toString(),
                        editTexts.get(2).getText().toString());
                break;
            default:
                break;
        }
    }

    private ISignPresenter signPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_icon_return_left_01);
        }

        signPresenter = new SignPresenter(this,this);
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
    public void getSignMsg(boolean status, int msg) {
        if (!status) {
            switch (msg) {
                case 0:
                    alertDialogSend("用户名错误","用户名必须大于4位小于20位，且只能输入数字/英文/下划线！");
                    break;
                case 1:
                    alertDialogSend("密码错误","密码必须大于6位小于20位，且只能输入数字/英文！");
                    break;
                case 2:
                    alertDialogSend("验证错误","密码与确认密码不相同！");
                    break;
                case 3:
                    alertDialogSend("注册错误","本机已有用户注册，请勿重复注册！ ");
                    break;
                case 4:
                    alertDialogSend("注册错误","注册失败！");
                    break;
                default:
                    break;
            }
        } else {
            alertDialogSendActivity("注册成功","注册完成！");
        }
    }

    private void alertDialogSend(String str, String str1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str).setMessage(str1).setPositiveButton(" 确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    private void alertDialogSendActivity(String str, String str1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str).setMessage(str1).setPositiveButton(" 确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SignActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).show();
    }
}

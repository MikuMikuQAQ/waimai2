package com.waimai.Sign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Sign.Presenter.ISignPresenter;
import com.waimai.Sign.Presenter.SignPresenter;
import com.waimai.Sign.View.ISignView;
import com.waimai.View.R;

import java.util.List;

public class SignActivity extends AppCompatActivity implements ISignView {

    @BindViews({R.id.register_editText1,R.id.register_editText2,R.id.register_editText3})
    public List<AppCompatEditText> editTexts;

    @OnClick({R.id.register_return,R.id.register_button})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.register_return:
                finish();
                break;
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

        signPresenter = new SignPresenter(this,this);
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
            alertDialogSend("注册成功","注册完成！");
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
}

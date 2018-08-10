package com.waimai.Set;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Set.Presenter.IModifyPresenter;
import com.waimai.Set.Presenter.ModifyPresenter;
import com.waimai.Set.View.IModifyView;
import com.waimai.View.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyPwdActivity extends AppCompatActivity implements IModifyView {

    private ActionBar actionBar;

    private IModifyPresenter modifyPresenter;

    private String userName;

    @BindViews({R.id.password_edit1, R.id.password_edit2, R.id.password_edit3})
    List<AppCompatEditText> editTexts;

    @BindView(R.id.password_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd);

        modifyPresenter = new ModifyPresenter(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_icon_return_left_01);
        }

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.password_save:
                modifyPresenter.updatePwd(userName,editTexts.get(0).getText().toString(),editTexts.get(1).getText().toString(),editTexts.get(2).getText().toString());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void getVerifyMsg(boolean status, int msg) {
        if (!status) {
            switch (msg) {
                case 0:
                    alertDialogSend("验证错误", "新密码与确认密码不相同！");
                    break;
                case 1:
                    alertDialogSend("密码错误", "密码必须大于6位小于20位，且只能输入数字/英文！");
                    break;
                case 2:
                    alertDialogSend("验证错误", "旧密码错误！");
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(this, "密码更新成功", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < editTexts.size(); i++) {
                editTexts.get(i).setText("");
            }
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

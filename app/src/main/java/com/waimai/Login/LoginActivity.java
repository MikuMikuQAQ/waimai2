package com.waimai.Login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.waimai.Login.Presenter.ILoginPresenter;
import com.waimai.Login.Presenter.LoginPresenter;
import com.waimai.Login.View.ILoginView;
import com.waimai.Main.MainActivity;
import com.waimai.Sign.SignActivity;
import com.waimai.View.R;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindViews({R.id.buttonLogin, R.id.buttonSign})
    public List<AppCompatButton> buttonList;

    @BindViews({R.id.login_edit1, R.id.login_edit2})
    public List<AppCompatEditText> viewList;

    @OnClick({R.id.buttonLogin, R.id.buttonSign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                userName = viewList.get(0).getText().toString();
                userPassword = viewList.get(1).getText().toString();
                loginPresenter.verifyLogin(userName, userPassword);
                break;
            case R.id.buttonSign:
                startActivity(new Intent(this, SignActivity.class));
                break;
            default:
                break;
        }
    }

    private String userName;

    private String userPassword;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SQLiteDatabase db = Connector.getDatabase();

        loginPresenter = new LoginPresenter(this, this);

        if (loginPresenter.checkLogin()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        ButterKnife.bind(this);

    }

    @Override
    public void getVerifyMsg(boolean status, int msg) {
        if (!status) {
            switch (msg) {
                case 0:
                    Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "未检测到注册信息，请注册使用", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "该用户名未注册", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}

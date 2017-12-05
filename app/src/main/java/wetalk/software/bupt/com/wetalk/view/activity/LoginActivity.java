package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.presenter.LoginPresenter;
import wetalk.software.bupt.com.wetalk.view.viewinter.ViewInter;

public class LoginActivity extends AppCompatActivity implements ViewInter {
    EditText et_account,et_password;
    Button btn_login;
    TextView tv_forgetpassword;
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        //初始化事件
        event();
        //建立与presenter层的关系，创建presenter对象

    }

    public void initView(){
        et_account = (EditText) findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_forgetpassword = (TextView) findViewById(R.id.btn_forgetpass);
    }
    //事件响应
    private void event(){
        /*
        登陆事件响应
         */
        btn_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = et_account.getText().toString();
                String password = et_password.getText().toString();
                if (name.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setTitle("警告");
                    dialog.setMessage("用户名或密码不能为空请重试");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                } else {
                    presenter.login();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                }

            }
        });

        /*
         *忘记密码事件响应
         */
        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("忘记密码？");
                dialog.setMessage("请联系管理员：\n电话：137xxxxxxxx\n邮箱：fhsdakhfk@163.com");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public String getUsername(){
        return et_account.getText().toString();
    }
    @Override
    public String getPassword(){
        return et_password.getText().toString();
    }
    @Override
    public void clearUserName() {
        et_account.setText("");
    }

    @Override
    public void clearUserPass() {
        et_password.setText("");
    }


    @Override
    public void successHint(User user, String tag) {
        Toast.makeText(this,"用户" + user.getUserName() + tag + "成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failHint(User user,String tag) {
        //  Toast.makeText(this,"用户" + user.getUserName() + tag + "失败,密码或账号不正确，maybe not this user",Toast.LENGTH_LONG).show();
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setTitle("警告");
        dialog.setMessage("登录失败\n密码或账号不正确");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
}

package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;

public class MyInfoActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mDepartment;
    private TextView mEmail;
    private TextView mPhone;
    private ImageView mHead;
    private Button mButtonChangePassword;
    private Button mButtonUpdateEmail;
    private Button mButtonUpdatePhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        showMyInfo();
        initButton();


    }

    //设置各个button的逻辑
    private  void initButton(){
        //找到各个button
        mButtonChangePassword = (Button) findViewById(R.id.id_change_password);
        mButtonUpdateEmail = (Button) findViewById(R.id.id_bt_update_email);
        mButtonUpdatePhoneNumber = (Button) findViewById(R.id.id_bt_update_phonenumber);

        //修改密码按钮
        mButtonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User demo = new User("刘楠",1,"15638819563","737720233@qq.com",R.mipmap.ic_launcher);
                String email = demo.getEmail().toString();
                Intent intent = new Intent(MyInfoActivity.this,ChangePasswordActivity.class);
                intent.putExtra("extra_email",email);
                startActivity(intent);
            }
        });

        //用户更新手机号
        mButtonUpdatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User demo = new User("刘楠",1,"15638819563","737720233@qq.com",R.mipmap.ic_launcher);
                String phoneNumber = demo.getPhone();
                Intent intent = new Intent(MyInfoActivity.this,UpdatePhoneNumberActivity.class);
                intent.putExtra("extra",phoneNumber);
                startActivity(intent);


            }
        });

        //用户更新邮箱
        mButtonUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User demo =new User("刘楠",1,"15638819563","737720233@qq.com",R.mipmap.ic_launcher);
                String email = demo.getEmail().toString();
                Intent intent = new Intent(MyInfoActivity.this,UpdateEmailActivity.class);
                intent.putExtra("extra_email",email);
                startActivity(intent);

            }
        });


    }

    //展示用户信息
    private   void showMyInfo(){
        //假设用户demo
        User demo = new User("刘楠",1,"15638819563","737720233@qq.com",R.mipmap.ic_launcher);

        // 找到各控件
        mName = (TextView) findViewById(R.id.id_show_name);
        mHead = (ImageView) findViewById(R.id.id_head_portrait);
        mEmail = (TextView) findViewById(R.id.id_show_email);
        mPhone = (TextView) findViewById(R.id.id_show_phonenumber);
        mDepartment = (TextView) findViewById(R.id.id_show_department);

        //显示demo用户的信息
        mName.setText(demo.getUserName().toString());
        mHead.setImageResource(demo.getUserHeader());
        mEmail.setText("邮箱："+demo.getEmail());
        mPhone.setText("手机号："+demo.getPhone());
        mDepartment.setText("所属部门:"+(demo.getDepartment_id()==1?"技术部":"其他部门"));


    }
}

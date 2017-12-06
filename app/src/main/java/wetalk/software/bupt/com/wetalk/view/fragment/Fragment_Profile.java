package wetalk.software.bupt.com.wetalk.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.view.activity.ChangePasswordActivity;
import wetalk.software.bupt.com.wetalk.view.activity.LoginActivity;
import wetalk.software.bupt.com.wetalk.view.activity.UpdateEmailActivity;
import wetalk.software.bupt.com.wetalk.view.activity.UpdatePhoneNumberActivity;

/**
 * Created by Administrator on 2017/12/5.
 */

public class Fragment_Profile extends Fragment {
    private TextView mName;
    private TextView mDepartment;
    private TextView mEmail;
    private TextView mPhone;
    private ImageView mHead;
    private ImageButton mButtonChangePassword;
    private ImageButton mButtonUpdateEmail;
    private ImageButton mButtonUpdatePhoneNumber;
    private ImageButton mButtonExit;
    private View view;
    private Context mContext;
    private Activity mActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_my_info,container,false);

        mContext=getContext();
        mActivity=getActivity();
        showMyInfo();
        initButton();
        return view;
    }

    //设置各个button的逻辑
    private  void initButton(){
        //找到各个button
        mButtonChangePassword = (ImageButton) view.findViewById(R.id.id_change_password);
        mButtonUpdateEmail = (ImageButton) view.findViewById(R.id.id_bt_update_email);
        mButtonUpdatePhoneNumber = (ImageButton)view.findViewById(R.id.id_bt_update_phonenumber);
        mButtonExit = (ImageButton) view.findViewById(R.id.id_exit);

        //修改密码按钮
        mButtonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User demo = new User("刘楠",1,"15638819563","737720233@qq.com",R.mipmap.ic_launcher);
                String email = demo.getEmail().toString();
                Intent intent = new Intent(mActivity,ChangePasswordActivity.class);
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
                Intent intent = new Intent(mActivity,UpdatePhoneNumberActivity.class);
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
                Intent intent = new Intent(mActivity,UpdateEmailActivity.class);
                intent.putExtra("extra_email",email);
                startActivity(intent);

            }
        });

        //退出按钮
        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    //展示用户信息
    private   void showMyInfo(){
        //假设用户demo
        User demo = new User("刘楠",1,"15638819563","737720233@qq.com",R.mipmap.a2);

        // 找到各控件
        mName = (TextView) view.findViewById(R.id.id_show_name);
        mHead = (ImageView) view.findViewById(R.id.id_head_portrait);
        mEmail = (TextView) view.findViewById(R.id.id_show_email);
        mPhone = (TextView) view.findViewById(R.id.id_show_phonenumber);
        mDepartment = (TextView) view.findViewById(R.id.id_show_department);

        //显示demo用户的信息
        mName.setText(demo.getUserName().toString());
        mHead.setImageResource(demo.getUserHeader());
        mEmail.setText(demo.getEmail());
        mPhone.setText(demo.getPhone());
        mDepartment.setText("部门:"+(demo.getDepartment_id()==1?"技术部":"其他部门"));
    }


}

package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView infoImage;
    private TextView infoName;
    private TextView infoJob;
    private TextView infoPhoneNumber;
    private TextView infoEmail;

    public static void actionStart(Context context,User user){
        Intent intent=new Intent(context,UserInfoActivity.class);
        intent.putExtra("name",user.getUserName());
        intent.putExtra("department",user.getAvatar());
        intent.putExtra("phoneNumber",user.getPhone());
        intent.putExtra("email",user.getEmail());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initTitle();
        initViews();
    }

    private void initTitle(){
        RelativeLayout rvRightImg=(RelativeLayout)findViewById(R.id.rv_img_right);
        rvRightImg.setVisibility(View.GONE);
        TextView txt=(TextView)findViewById(R.id.txt_title);
        txt.setText("好友资料");
    }

    private void initViews(){
        infoImage=(ImageView)findViewById(R.id.info_img);
        infoName=(TextView)findViewById(R.id.info_name);
        infoJob=(TextView)findViewById(R.id.info_department);
        infoPhoneNumber=(TextView)findViewById(R.id.info_phone_number);
        infoEmail=(TextView)findViewById(R.id.info_email);

        infoImage.setImageResource(R.drawable.head);
        infoName.setText(getIntent().getStringExtra("name"));
        infoJob.setText(getIntent().getStringExtra("department"));
        infoPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
        infoEmail.setText(getIntent().getStringExtra("email"));

    }
}

package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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
        intent.putExtra("job",user.getAvatar());
        intent.putExtra("phoneNumber",user.getPhone());
        intent.putExtra("email",user.getEmail());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initViews();
    }

    private void initViews(){
        infoImage=(ImageView)findViewById(R.id.info_image);
        infoName=(TextView)findViewById(R.id.info_name);
        infoJob=(TextView)findViewById(R.id.info_job);
        infoPhoneNumber=(TextView)findViewById(R.id.info_phone_number);
        infoEmail=(TextView)findViewById(R.id.info_email);

        infoImage.setImageResource(R.drawable.head);
        infoName.setText(getIntent().getStringExtra("name"));
        infoJob.setText(getIntent().getStringExtra("job"));
        infoPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
        infoEmail.setText(getIntent().getStringExtra("email"));

    }
}

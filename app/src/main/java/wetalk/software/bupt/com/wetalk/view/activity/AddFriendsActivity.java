package wetalk.software.bupt.com.wetalk.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;

public class AddFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        initTitle();
        Button bt=(Button)findViewById(R.id.bt_search);
        final User user=new User();
        user.setUserName("天下");
        user.setEmail("jhaoma@qq.com");
        user.setPhone("17633907915");
        user.setAvatar("技术部");
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserInfoActivity.actionStart(AddFriendsActivity.this,user,"add");
            }
        });
    }

    private void initTitle(){
        RelativeLayout rvRight=(RelativeLayout)findViewById(R.id.rv_img_right);
        rvRight.setVisibility(View.GONE);
        TextView txt=(TextView)findViewById(R.id.txt_title);
        txt.setText("添加朋友");
        RelativeLayout rvLeft=(RelativeLayout)findViewById(R.id.rv_img_back);
        rvLeft.setVisibility(View.VISIBLE);
    }
}

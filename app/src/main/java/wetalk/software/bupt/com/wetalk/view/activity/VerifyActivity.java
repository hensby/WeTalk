package wetalk.software.bupt.com.wetalk.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import wetalk.software.bupt.com.wetalk.R;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        initTitle();
        Button bt=(Button)findViewById(R.id.bt_verify);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(VerifyActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initTitle(){
        RelativeLayout rvRight=(RelativeLayout)findViewById(R.id.rv_img_right);
        rvRight.setVisibility(View.GONE);
        TextView txt=(TextView)findViewById(R.id.txt_title);
        txt.setText("验证申请");
        RelativeLayout rvLeft=(RelativeLayout)findViewById(R.id.rv_img_back);
        rvLeft.setVisibility(View.VISIBLE);
    }
}

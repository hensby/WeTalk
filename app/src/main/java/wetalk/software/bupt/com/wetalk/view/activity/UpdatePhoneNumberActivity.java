package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;

public class UpdatePhoneNumberActivity extends AppCompatActivity {
    private TextView mPhoneNumber;
    private ImageButton mReturn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        setContentView(R.layout.activity_update_phone_number);
        mPhoneNumber = (TextView) findViewById(R.id.id_original_phonenumber);
        final Intent intent = getIntent();
        String data = intent.getStringExtra("extra");
        mPhoneNumber.setText(data);

        mReturn1 = (ImageButton) findViewById(R.id.id_return_button1);
        mReturn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}

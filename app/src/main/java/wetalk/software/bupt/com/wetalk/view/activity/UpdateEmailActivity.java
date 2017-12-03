package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;

public class UpdateEmailActivity extends AppCompatActivity {

    private TextView mEmail;
    private ImageButton mReturn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        setContentView(R.layout.activity_update_email);
        mEmail = (TextView) findViewById(R.id.id_original_email);
        intent = getIntent();
        String data = intent.getStringExtra("extra_email");
        mEmail.setText(data);

        mReturn = (ImageButton) findViewById(R.id.id_return_button);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}

package wetalk.software.bupt.com.wetalk.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import wetalk.software.bupt.com.wetalk.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageButton mReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        setContentView(R.layout.activity_change_password);
        mReturn = (ImageButton) findViewById(R.id.id_return_button2);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

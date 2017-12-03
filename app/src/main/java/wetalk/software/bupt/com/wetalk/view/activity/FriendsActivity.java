package wetalk.software.bupt.com.wetalk.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;

public class FriendsActivity extends AppCompatActivity {
    private ImageView imageRight;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        txtTitle=(TextView) findViewById(R.id.txt_title);
        imageRight=(ImageView) findViewById(R.id.img_right);
        txtTitle.setText("通讯录");
        imageRight.setVisibility(View.VISIBLE);
        imageRight.setImageResource(R.drawable.icon_add);
    }
}

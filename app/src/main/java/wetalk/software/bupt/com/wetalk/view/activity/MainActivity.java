package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import wetalk.software.bupt.com.wetalk.R;


public class MainActivity extends AppCompatActivity {
    private Button buttonMessage;
    private Button buttonFriends;
    private Button buttonMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonFriends = (Button) findViewById(R.id.button2);
        buttonMessage = (Button) findViewById(R.id.button1);
        buttonMe = (Button) findViewById(R.id.button3);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChooseMemberActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        buttonFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FriendsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        buttonMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyInfoActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }
}

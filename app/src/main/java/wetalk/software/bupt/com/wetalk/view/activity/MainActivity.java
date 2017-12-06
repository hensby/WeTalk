package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.view.dialog.TitleMenu.ActionItem;
import wetalk.software.bupt.com.wetalk.view.dialog.TitleMenu.TitlePopup;
import wetalk.software.bupt.com.wetalk.view.fragment.Fragment_Friends;
import wetalk.software.bupt.com.wetalk.view.fragment.Fragment_Msg;
import wetalk.software.bupt.com.wetalk.view.fragment.Fragment_Profile;


public class MainActivity extends AppCompatActivity {
    private TextView txt_title;
    private RelativeLayout rv_img_right;
    private TextView unreaMsgdLabel;// 未读消息textview
    private TextView unreadAddressLable;// 未读通讯录textview
    private Fragment[] fragments;
    public Fragment_Msg homefragment;
    private Fragment_Friends contactlistfragment;
    private Fragment_Profile profilefragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int currentIndex,index;
    TitlePopup titlePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
        initTabView();
        initPopWindow();
        updateUnreadLabel();
    }

    private void initTitle(){
        txt_title = (TextView) findViewById(R.id.txt_title);
        rv_img_right=(RelativeLayout) findViewById(R.id.rv_img_right);
        txt_title.setText("会话");
        rv_img_right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                titlePopup.show(findViewById(R.id.layout_bar));
            }
        });
    }

    private void initTabView() {
        unreaMsgdLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        homefragment = new Fragment_Msg();
        contactlistfragment = new Fragment_Friends();
        profilefragment = new Fragment_Profile();
        fragments = new Fragment[] { homefragment, contactlistfragment, profilefragment };
        imagebuttons = new ImageView[3];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[3];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(0xFF45C01A);
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, contactlistfragment)
                .add(R.id.fragment_container, profilefragment)
                .hide(contactlistfragment).hide(profilefragment)
                .show(homefragment).commit();
        currentIndex=0;
    }

    public void onTabClicked(View view){
        switch (view.getId()){

            case R.id.re_weixin:
                index=0;
                txt_title.setText("会话");
                break;

            case R.id.re_contact_list:
                index=1;
                txt_title.setText("通讯录");
                break;

            case R.id.re_profile:
                index=2;
                txt_title.setText("我");
                break;
        }
        if(currentIndex!=index){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentIndex]);
            if(!fragments[index].isAdded()){
                ft.add(R.id.fragment_container,fragments[index]);
            }
            ft.show(fragments[index]).commit();
            imagebuttons[currentIndex].setSelected(false);
            imagebuttons[index].setSelected(true);
            textviews[currentIndex].setTextColor(0xFF999999);
            textviews[index].setTextColor(0xFF45C01A);
            currentIndex=index;
        }
    }

    private void initPopWindow() {
        // 实例化标题栏弹窗
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(onitemClick);
        // 给标题栏弹窗添加子类
        titlePopup.addAction(new ActionItem(this, R.string.menu_groupchat,
                R.drawable.icon_menu_group));
        titlePopup.addAction(new ActionItem(this, R.string.menu_addfriend,
                R.drawable.icon_menu_addfriend));
    }

    private TitlePopup.OnItemOnClickListener onitemClick = new TitlePopup.OnItemOnClickListener() {

        @Override
        public void onItemClick(ActionItem item, int position) {
            // mLoadingDialog.show();
            switch (position) {
                case 0:// 发起群聊
                    Intent intent=new Intent(MainActivity.this, ChooseMemberActivity.class);
                    startActivity(intent);
                    break;
                case 1:// 添加朋友
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取未读消息数
     */
    public void updateUnreadLabel() {
        int count = 7;
//        count = EMChatManager.getInstance().getUnreadMsgsCount();
        if (count > 0) {
            unreaMsgdLabel.setText(String.valueOf(count));
            unreaMsgdLabel.setVisibility(View.VISIBLE);
        } else {
            unreaMsgdLabel.setVisibility(View.INVISIBLE);
        }
    }
}

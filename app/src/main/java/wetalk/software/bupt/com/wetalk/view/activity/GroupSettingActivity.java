package wetalk.software.bupt.com.wetalk.view.activity;//package wetalk.software.bupt.com.wetalk.view.activity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import java.util.ArrayList;
//import java.util.List;
//
//import wetalk.software.bupt.com.wetalk.R;
//import wetalk.software.bupt.com.wetalk.model.po.Group;
//import wetalk.software.bupt.com.wetalk.model.po.User;
//
///**
// * Created by whc07 on 2017/12/7.
// */
//
//public class GroupSettingActivity extends AppCompatActivity implements OnClickListener {
//    private ImageView img_back;
//    private TextView tv_groupname;
//    private TextView txt_title;// 标题，成员总数
//    int m_total = 0;// 成员总数
//    private ExpandGridView gridview;// 成员列表
//    // 修改群名称、置顶、、、、
//    private RelativeLayout re_change_groupname;
//    private RelativeLayout rl_switch_chattotop;
//    private RelativeLayout rl_switch_block_groupmsg;
//    private RelativeLayout re_clear;
//    private CheckBox check_top, check_closetip;
//    private Button exitBtn;
//    private String hxid;
//    private String group_name;// 群名称
//    boolean is_admin = false;// 是否是管理员
//    List<User> members = new ArrayList<User>();
//    String longClickUsername = null;
//
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_groupsetting);
//        super.onCreate(savedInstanceState);
//        initControl();
//    }
//
//    private void  initData(){
//        final User angelbaby = new User("王恒超",R.mipmap.a1);
//        User tangyan = new User("刘楠",R.mipmap.a2);
//        User zhaoliying = new User("王朝晖",R.mipmap.a3);
//        User gaoyuanyuan = new User("张杰",R.mipmap.a4);
//        User cat = new User("马家豪",R.mipmap.a5);
//        members = new ArrayList<User>();
//        members.add(angelbaby);
//        members.add(tangyan);
//        members.add(zhaoliying);
//        members.add(gaoyuanyuan);
//        members.add(cat);
//    }
//    private void showGroupInfo(){
//
//        Group demo = new Group(1,"androidgroup",members);
//        //控件
//        tv_groupname =(TextView) findViewById(R.id.re_change_groupname);
//        gridview = (ExpandGridView) findViewById(R.id.gridview);
//        //内容
//        tv_groupname.setText(demo.getGroup_name().toString());
//        gridview.
//
//
//    }
//
//
//    protected void initControl() {
//        txt_title = (TextView) findViewById(R.id.txt_title);
//        txt_title.setText("聊天信息");
//        img_back = (ImageView) findViewById(R.id.img_back);
//        img_back.setVisibility(View.VISIBLE);
//        tv_groupname = (TextView) findViewById(R.id.txt_groupname);
//        gridview = (ExpandGridView) findViewById(R.id.gridview);
//
//        re_change_groupname = (RelativeLayout) findViewById(R.id.re_change_groupname);
//        rl_switch_chattotop = (RelativeLayout) findViewById(R.id.rl_switch_chattotop);
//        rl_switch_block_groupmsg = (RelativeLayout) findViewById(R.id.rl_switch_block_groupmsg);
//        re_clear = (RelativeLayout) findViewById(R.id.re_clear);
//
//        exitBtn = (Button) findViewById(R.id.btn_exit_grp);
//    }
//
//
//    private void showMembers(List<User> members) {
//        adapter = new GridAdapter(this, members);
//        gridview.setAdapter(adapter);}
//
//    @Override
//    public void onClick(View view) {
//
//    }
//    private class GridAdapter extends BaseAdapter {
//
//        //public boolean isInDeleteMode;
//        private List<User> objects;
//        Context context;
//
//        public GridAdapter(Context context, List<User> objects) {
//
//            this.objects = objects;
//            this.context = context;
//            //isInDeleteMode = false;
//        }
//
//
//
//        @Override
//        public View getView(final int position, View convertView,
//                            final ViewGroup parent) {
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(
//                        R.layout.item_chatsetting_gridview, null);
//            }
//            ImageView iv_avatar = (ImageView) convertView
//                    .findViewById(R.id.iv_avatar);
//            TextView tv_username = (TextView) convertView
//                    .findViewById(R.id.tv_username);
//            ImageView badge_delete = (ImageView) convertView
//                    .findViewById(R.id.badge_delete);
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//}
//


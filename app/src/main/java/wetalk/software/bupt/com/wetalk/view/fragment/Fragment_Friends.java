package wetalk.software.bupt.com.wetalk.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.adapter.ContactAdapter;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.util.PinYinUtil;
import wetalk.software.bupt.com.wetalk.view.activity.UserInfoActivity;
import wetalk.software.bupt.com.wetalk.view.viewinter.SideBar;

/**
 * Created by Administrator on 2017/11/29.
 */

public class Fragment_Friends extends Fragment implements SideBar.OnTextViewChange,
        View.OnClickListener, AdapterView.OnItemClickListener{
    private View layout, layout_head;
    private ListView lvContact;
    private SideBar indexBar;
    private TextView mDialogText;


    private LayoutInflater inflater;
    private List<User> userList;
    private ContactAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater=inflater;
        layout = inflater.inflate(R.layout.fragment_friends, null);
        initViews();
        initData();
        setOnListener();
        return layout;
    }

    private void initViews() {


        lvContact = (ListView) layout.findViewById(R.id.lvContact);
        mDialogText = (TextView) layout.findViewById(R.id.tv_char);
        indexBar = (SideBar) layout.findViewById(R.id.sideBar);
        indexBar.setTextView(mDialogText);
        layout_head = inflater.inflate(R.layout.layout_head_friend, null);
        lvContact.addHeaderView(layout_head);

    }


    @Override
    public void onTextChange(String s) {
        for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);//获取user信息
            String catalog = PinYinUtil.converterToFirstSpell(user.getUserName())
                    .substring(0, 1);

            if (catalog!= null && catalog.equals(s)) {//判断首字母不为空，并且首字母与弹出TextView字符串相等
                Log.d("TAG",user.getUserName());
                final int finalI = i;
                lvContact.post(new Runnable() {
                    @Override
                    public void run() {
                        lvContact.requestFocusFromTouch();//获取焦点
                        lvContact.setSelection(finalI+1);//跳转到该首字母的item
                    }
                });
                adapter.notifyDataSetChanged();//更新ListView
                break;
            }

        }
    }

    private void initData(){
        String[] name={"张三","李四","王二","麻子","快递员","申通","建设银行","圆通","阿里巴巴","百度","腾讯","华为","菜鸟","滴滴","饿了么","飞猪","京东","今日头条"};
        this.userList=new ArrayList<>();
        for (int i=0;i<name.length;i++){
            User user=new User();
            user.setUserName(name[i]);
            user.setEmail("jhaoma@qq.com");
            user.setAvatar("安卓开发工程师");
            user.setPhone("17633907915");
            userList.add(user);
        }
        adapter=new ContactAdapter(getActivity(),userList);
        lvContact.setAdapter(adapter);
    }

    private void setOnListener() {
        lvContact.setOnItemClickListener(this);
        indexBar.setOnTextViewChange(this);
        layout_head.findViewById(R.id.layout_addfriend).setOnClickListener(this);
        layout_head.findViewById(R.id.layout_search).setOnClickListener(this);
        layout_head.findViewById(R.id.layout_group).setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position-1>=0){
            User user=userList.get(position-1);
            UserInfoActivity.actionStart(getActivity(),user);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.layout_search:// 搜索好友
//                Utils.start_Activity(getActivity(), SearchActivity.class);
//                break;
//            case R.id.layout_addfriend:// 添加好友
//                Utils.start_Activity(getActivity(), NewFriendsListActivity.class);
//                break;
//            case R.id.layout_group:// 群聊
//                Utils.start_Activity(getActivity(), GroupListActivity.class);
//                break;
//            default:
//                break;
        }
    }

}

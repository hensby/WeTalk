package wetalk.software.bupt.com.wetalk.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.adapter.ContactAdapter;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.util.CharacterParser;
import wetalk.software.bupt.com.wetalk.util.PinYinUtil;
import wetalk.software.bupt.com.wetalk.util.PinyinComparator;
import wetalk.software.bupt.com.wetalk.view.activity.GroupListActivity;
import wetalk.software.bupt.com.wetalk.view.activity.UserInfoActivity;
import wetalk.software.bupt.com.wetalk.widget.ClearEditText;
import wetalk.software.bupt.com.wetalk.widget.SideBar;

/**
 * 通讯录Fragment
 */

public class Fragment_group extends Fragment implements SideBar.OnTextViewChange,
        View.OnClickListener{
    private View layout, layout_head;
    private ListView lvContact;
    private SideBar indexBar;
    private TextView mDialogText;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换为拼音的类
     */
    private CharacterParser characterParser=new CharacterParser();

    private LayoutInflater inflater;
    private List<User> userList;//朋友信息
    private ContactAdapter adapter;//通讯录listview的适配器

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
        //layout_head = inflater.inflate(R.layout.layout_head_friend, null);
        mClearEditText = (ClearEditText)layout.findViewById(R.id.et_msg_search);
        //lvContact.addHeaderView(layout_head);
    }



    private void initData(){
        String[] name={"androidgroup","iosgroup","webgroup"};
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

    /**
     * 设置监听器
     */
    private void setOnListener() {
        //设置好友目录的子项点击事件
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position-1>=0){
                    User user=userList.get(position-1);
                    UserInfoActivity.actionStart(getActivity(),user,"look");
                }
            }
        });
        //设置侧边栏的点击事件
        indexBar.setOnTextViewChange(this);
        //设置添加好友的点击事件
        //layout_head.findViewById(R.id.layout_addfriend).setOnClickListener(this);
        //设置群聊的点击事件
        //layout_head.findViewById(R.id.layout_group).setOnClickListener(this);
        //设置搜索框的文本改变事件
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {filterData(s.toString());}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * 侧边栏的文本改变事件
     */
    @Override
    public void onTextChange(String s) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);//获取user信息
            String catalog = PinYinUtil.converterToFirstSpell(user.getUserName()).substring(0, 1);

            if (catalog!= null && catalog.equals(s)) {//判断首字母不为空，并且首字母与弹出TextView字符串相等
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

    /**
     *添加好友与群聊的点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    /**
     * 搜索框文本改变事件
     */
    private void filterData(String filterStr) {
        List<User> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = userList;
        } else {
            filterDateList.clear();
            for (User sortModel : userList) {
                String name = sortModel.getUserName();
                if (name != null) {
                    if (name.indexOf(filterStr.toString()) != -1
                            || characterParser.getSelling(name).startsWith(
                            filterStr.toString())) {
                        filterDateList.add(sortModel);
                    }
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, new PinyinComparator());
        adapter.updateListView(filterDateList);
    }

}

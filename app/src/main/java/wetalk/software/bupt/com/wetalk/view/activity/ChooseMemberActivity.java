package wetalk.software.bupt.com.wetalk.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;

public class ChooseMemberActivity extends ActionBarActivity {

    // 可滑动的显示选中用户的View
    private LinearLayout menuLinerLayout;


    private ListView listView;
    private List<User> allUserList;
    private EditText editText;
    private TextView tv_checked;
    private ImageView iv_search;
    private ListAdapter adapter;
    private List<String> addList = new ArrayList<String>();
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_member);
        initView();
        initData();
    }

    private void initData() {
        final User angelbaby = new User("王恒超",R.mipmap.a1);
        User tangyan = new User("刘楠",R.mipmap.a2);
        User zhaoliying = new User("王朝晖",R.mipmap.a3);
        User gaoyuanyuan = new User("张杰",R.mipmap.a4);
        User cat = new User("马家豪",R.mipmap.a5);
        allUserList = new ArrayList<User>();
        allUserList.add(angelbaby);
        allUserList.add(tangyan);
        allUserList.add(zhaoliying);
        allUserList.add(gaoyuanyuan);
        allUserList.add(cat);
        adapter = new ListAdapter(ChooseMemberActivity.this,allUserList);
        listView.setAdapter(adapter);

        //搜索栏搜索
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    String str_s = editText.getText().toString().trim();
                    List<User> user_temp = new ArrayList<User>();
                    for (User user : allUserList) {
                        String userName = user.getUserName();
                        if (userName.contains(str_s)) {
                            user_temp.add(user);
                        }
                        adapter = new ListAdapter(ChooseMemberActivity.this, user_temp);
                        listView.setAdapter(adapter);
                    }
                } else {
                    adapter = new ListAdapter(ChooseMemberActivity.this, allUserList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, long id) {
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.toggle();
            }
        });


    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
        menuLinerLayout = (LinearLayout) findViewById(R.id.linearLayoutMenu);
        editText = (EditText) findViewById(R.id.et_search);
        tv_checked = (TextView) findViewById(R.id.tv_checked);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        tv_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //TODO 需要在intent加入user
                // intent.putExtra()
                intent.setClass(ChooseMemberActivity.this, ChatActivity.class);
                ChooseMemberActivity.this.startActivity(intent);
            }
        });

    }

    //显示选择的头像
    private void showCheckImage(Bitmap bitmap, User glufineid) {
        total++;
        // 包含TextView的LinearLayout
        // 参数设置
        android.widget.LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
                75, 75, 1);
        View view = LayoutInflater.from(this).inflate(
                R.layout.header_item, null);
        ImageView images = (ImageView) view.findViewById(R.id.iv_avatar);
        menuLinerLayoutParames.setMargins(6, 6, 6, 6);

        // 设置id，方便后面删除
        view.setTag(glufineid);
        if (bitmap == null) {
            images.setImageResource(R.mipmap.default_useravatar);
        } else {
            images.setImageBitmap(bitmap);
        }

        menuLinerLayout.addView(view, menuLinerLayoutParames);
        tv_checked.setText("确定(" + total + ")");
        if (total > 0) {
            if (iv_search.getVisibility() == View.VISIBLE) {
                iv_search.setVisibility(View.GONE);
            }
        }
        addList.add(glufineid.getUserName());
    }

    //删除选择的头像
    private void deleteImage(User glufineid) {
        View view = (View) menuLinerLayout.findViewWithTag(glufineid);

        menuLinerLayout.removeView(view);
        total--;
        tv_checked.setText("确定(" + total + ")");
        addList.remove(glufineid.getUserName());
        if (total < 1) {
            if (iv_search.getVisibility() == View.GONE) {
                iv_search.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * adapter
     */
    private class ListAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context context;
        private List<User> list = new ArrayList<User>();

        public ListAdapter(Context context,List<User> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public User getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            User user = getItem(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.listitem,null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview);
                if (addList != null && addList.contains(user.getUserName())) {
                    checkBox.setChecked(true);
                }
                if (checkBox != null){
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                Bitmap bitmap = null;
                                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                showCheckImage(bitmap, list.get(position));
                            } else {
                                // 用户显示在滑动栏删除
                                deleteImage(list.get(position));
                            }
                        }
                    });
                }
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setImageResource(user.getUserHeader());
            viewHolder.textView.setText(user.getUserName());
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }
}


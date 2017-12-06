package wetalk.software.bupt.com.wetalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.util.PinYinUtil;
import wetalk.software.bupt.com.wetalk.util.PinyinComparator;
import wetalk.software.bupt.com.wetalk.util.ViewHolder;


/**
 * Created by Administrator on 2017/11/30.
 */

public class ContactAdapter extends BaseAdapter  {
    private Context mContext;
    private List<User> UserInfos;// 好友信息

    public ContactAdapter(Context mContext, List<User> UserInfos) {
        this.mContext = mContext;
        this.UserInfos = UserInfos;
        // 排序(实现了中英文混排)
        Collections.sort(UserInfos, new PinyinComparator());
    }

    @Override
    public int getCount() {
        return UserInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return UserInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = UserInfos.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.contact_item, null);
        }
        ImageView ivAvatar = ViewHolder.get(convertView,
                R.id.contactitem_avatar_iv);
        TextView tvCatalog = ViewHolder.get(convertView,
                R.id.contactitem_catalog);
        TextView tvNick = ViewHolder.get(convertView, R.id.contactitem_nick);
        String catalog = PinYinUtil.converterToFirstSpell(user.getUserName())
                .substring(0, 1);
        if (position == 0) {
            tvCatalog.setVisibility(View.VISIBLE);
            tvCatalog.setText(catalog);
        } else {
            User Nextuser = UserInfos.get(position - 1);
            String lastCatalog = PinYinUtil.converterToFirstSpell(
                    Nextuser.getUserName()).substring(0, 1);
            if (catalog.equals(lastCatalog)) {
                tvCatalog.setVisibility(View.GONE);
            } else {
                tvCatalog.setVisibility(View.VISIBLE);
                tvCatalog.setText(catalog);
            }
        }

        ivAvatar.setImageResource(R.drawable.head);
        tvNick.setText(user.getUserName());
        return convertView;
    }

    public void updateListView(List<User> list){
        UserInfos=list;
        notifyDataSetChanged();
    }

}

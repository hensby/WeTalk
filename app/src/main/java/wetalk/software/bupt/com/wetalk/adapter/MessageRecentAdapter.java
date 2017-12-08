package wetalk.software.bupt.com.wetalk.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.application.ImageLoadOptions;
import wetalk.software.bupt.com.wetalk.model.po.Message;
import wetalk.software.bupt.com.wetalk.model.po.RecentContacts;



public class MessageRecentAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<RecentContacts> mData;
    private Context mContext;
	
	public MessageRecentAdapter(Context context, List<RecentContacts> objects) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mData = objects;

	}

    public MessageRecentAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		RecentContacts recentContacts = getItem(position);//获取当前的MessageRecent实例
//		View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
//		ImageView avator = (ImageView) view.findViewById(R.id.iv_recent_avatar);
//		TextView contactname = (TextView) view.findViewById(R.id.tv_recent_name);
//		TextView lastmessage = (TextView) view.findViewById(R.id.tv_recent_msg);
//		TextView lastcontacttime = (TextView) view.findViewById(R.id.tv_recent_time);
////		TextView isunread = (TextView) view.findViewById(R.id.tv_recent_unread);
//		avator.setImageResource(recentContacts.getContacts_avator());
//		contactname.setText(recentContacts.getContacts_name());
//		lastmessage.setText(recentContacts.getLastmessage());
//		lastcontacttime.setText((int) recentContacts.getLastContacts_time());
//		return view;

        RecentContacts item = mData.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_conversation, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv_recent_avatar = (ImageView)convertView.findViewById(R.id.iv_recent_avatar);
            viewHolder.tv_recent_name = (TextView) convertView.findViewById(R.id.tv_recent_name);
            viewHolder.tv_recent_msg = (TextView) convertView.findViewById(R.id.tv_recent_msg);
            viewHolder.tv_recent_time = (TextView) convertView.findViewById(R.id.tv_recent_time);
            viewHolder.tv_recent_unread = (TextView) convertView.findViewById(R.id.tv_recent_unread);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
//        ImageView iv_recent_avatar = ViewHolder.get(convertView, R.id.iv_recent_avatar);
//        TextView tv_recent_name = ViewHolder.get(convertView, R.id.tv_recent_name);
//        TextView tv_recent_msg = ViewHolder.get(convertView, R.id.tv_recent_msg);
//        TextView tv_recent_time = ViewHolder.get(convertView, R.id.tv_recent_time);
//        TextView tv_recent_unread = ViewHolder.get(convertView, R.id.tv_recent_unread);

        viewHolder.iv_recent_avatar.setImageResource(item.getContactsImgId());
        viewHolder.tv_recent_name.setText(item.getContactsName());
        viewHolder.tv_recent_msg.setText(item.getLastMessage());
        viewHolder. tv_recent_time.setText(item.getLastContactsTime());
//        viewHolder.tv_recent_unread.setText("[未读]");
        return convertView;
    }

    class ViewHolder{
        ImageView iv_recent_avatar;
        TextView tv_recent_name;
        TextView tv_recent_msg,tv_recent_time,tv_recent_unread;
    }
}

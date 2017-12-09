package wetalk.software.bupt.com.wetalk.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.adapter.MessageRecentAdapter;
import wetalk.software.bupt.com.wetalk.model.po.ChatUser;
import wetalk.software.bupt.com.wetalk.model.po.RecentContacts;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.view.activity.ChatActivity;


/** 最近会话
  * @ClassName: ConversationFragment
  * @Description: TODO
  * @author smile
  * @date 2014-6-7 下午1:01:37
  */
public class Fragment_Msg extends Fragment implements OnItemClickListener,OnItemLongClickListener{
	MessageRecentAdapter adapter;
	private List<RecentContacts> recentContacts = new ArrayList<>();
	ListView listview;
	private String msg;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_recent, container, false);
		msg="北京邮电大学软件学院";
		RecentContacts re = new RecentContacts(R.mipmap.a1,"刘楠",msg,new SimpleDateFormat("HH:mm").format(new Date()));
		recentContacts.add(re);
		listview = (ListView)view.findViewById(R.id.list);
		listview.setOnItemClickListener(this);
		listview.setOnItemLongClickListener(this);
		adapter = new MessageRecentAdapter(getActivity(),recentContacts);
		listview.setAdapter(adapter);
		return view;
	}

//	private void initView(){
//		listview = (ListView)this.findViewById(R.id.list);
//		listview.setOnItemClickListener(this);
//		listview.setOnItemLongClickListener(this);
//		adapter = new MessageRecentAdapter(getActivity(), R.layout.item_conversation, recentContacts);
//		listview.setAdapter(adapter);
//	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//传递给chatactivity的时候需要带上user,这个里还需要msg，别的不用
		ChatUser user=new ChatUser(11012,"刘楠","liunan","",1,"15600992328","liunan@qq.com");
		Intent intent = new Intent(getActivity(), ChatActivity.class);
		intent.putExtra("user",user);
		intent.putExtra("msg",msg);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return true;
	}

//	private void initRecentContacts(){
//		RecentContacts wanghengchao = new RecentContacts(R.mipmap.a1,"王恒超","吃饭了么？",lastMessage.getTime() );
//		recentContacts.add(wanghengchao);
//		RecentContacts wangzhaohui = new RecentContacts(R.mipmap.a3,"王朝晖","吃饭了么？",lastMessage.getTime() );
//		recentContacts.add(wangzhaohui);
//		RecentContacts majiahao = new RecentContacts(R.mipmap.a5,"马佳豪","吃饭了么？",lastMessage.getTime() );
//		recentContacts.add(majiahao);
//		RecentContacts liunan = new RecentContacts(R.mipmap.a2,"刘楠","吃饭了么？",lastMessage.getTime() );
//		recentContacts.add(liunan);
//		RecentContacts zhangjie = new RecentContacts(R.mipmap.a4,"张杰","吃饭了么？",lastMessage.getTime() );
//		recentContacts.add(zhangjie);
//	}
}

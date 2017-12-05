package wetalk.software.bupt.com.wetalk.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wetalk.software.bupt.com.wetalk.R;
import wetalk.software.bupt.com.wetalk.view.activity.ChooseMemberActivity;

/**
 * Created by Administrator on 2017/12/5.
 */

public class Fragment_Msg extends Fragment {
    private View view;
    private Activity mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_to_hengchao,container,false);
        mActivity=getActivity();
        Button bt=(Button)view.findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, ChooseMemberActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

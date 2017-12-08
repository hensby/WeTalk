package wetalk.software.bupt.com.wetalk.application;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wetalk.software.bupt.com.wetalk.listener.FindListener;
import wetalk.software.bupt.com.wetalk.model.po.ChatUser;
import wetalk.software.bupt.com.wetalk.model.po.User;

/**
 * Created by zhangjie on 2017/12/7.
 */

public class UserManager {
    //BmobPushManager<BmobInstallation> bmobPush;

    /**
     * 用户表中所关联的好友字段
     */
    public static final String COLUMN_NAME_CONTACTS = "contacts";

    /**
     * 用户表中所关联的黑名单列表
     */
    public static final String COLUMN_NAME_BLACKLIST = "blacklist";

    Context context;
    // 创建private static类实例
    private volatile static UserManager INSTANCE;
    //同步锁
    private static Object INSTANCE_LOCK = new Object();

    /**
     * 使用单例模式创建
     */
    public static UserManager getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (INSTANCE_LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new UserManager();
                }
                INSTANCE.init(context);
            }
        return INSTANCE;
    }

    /**
     *  初始化
     * @Title: init
     * @Description: TODO
     * @param  c
     * @return void
     * @throws
     */
    public void init(Context c) {
        this.context = c;
    }

}

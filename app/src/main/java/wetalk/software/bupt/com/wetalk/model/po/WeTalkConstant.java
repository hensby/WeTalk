package wetalk.software.bupt.com.wetalk.model.po;

import android.os.Environment;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkConstant {
    /**
     * IOS通讯的基础json字段
     */
    public static final String PUSH_BASE_APS = "aps";
    public static final String PUSH_BASE_SOUND = "sound";
    public static final String PUSH_BASE_ALERT = "alert";
    public static final String PUSH_BASE_BADGE = "badge";

    /**
     * 聊天消息的相关json字段:标示该消息是否属于标签消息
     */
    public static final String PUSH_KEY_TAG = "tag";
    /**
     * 聊天消息的相关json字段:标示该消息来源用户objectid
     */
    public static final String PUSH_KEY_TARGETID = "fId";//fromId

    /**
     * 聊天消息的相关json字段:标示该消息接收方
     */
    public static final String PUSH_KEY_TOID = "tId";//toId

    /**
     * 聊天消息的相关json字段:标示该消息的发送时间--解决出现两个用户之间相同消息的时间不对等导致无法更新消息状态的问题
     */
    public static final String PUSH_KEY_MSGTIME = "ft";//fromtime

    /**
     * 预留的额外字段
     */
    public static final String PUSH_KEY_EXTRA = "ex";//

    /**
     * 聊天消息的相关json字段:标示该消息类型
     */
    public static final String PUSH_KEY_MSGTYPE = "mt";//msgtype
    /**
     * 聊天消息的相关json字段:标示该消息内容
     */
    public static final String PUSH_KEY_CONTENT = "mc";//content

    /**
     * 聊天消息的相关json字段:标示该消息来源用户头像
     */
    public static final String PUSH_KEY_TARGETAVATAR = "fa";//fromavatar
    /**
     * 聊天消息的相关json字段:标示该消息来源用户名
     */
    public static final String PUSH_KEY_TARGETUSERNAME = "fu";//fromusername
    /**
     * 聊天消息的相关json字段:标示该消息来源用户昵称
     */
    public static final String PUSH_KEY_TARGETNICK = "fn";//fromnick

    //添加好友的相关组装字段-发送给对方，其携带的内容为请求方的内容
    /**
     * 好友请求的相关json字段:标识请求方Objectid
     */
    public static final String PUSH_ADD_ID = "fId";//fromId
    /**
     * 好友请求的相关json字段:标示请求方用户名
     */
    public static final String PUSH_ADD_FROM_NAME = "fu";//fromusername
    /**
     * 好友请求的相关json字段:标示请求方头像
     */
    public static final String PUSH_ADD_FROM_AVATAR = "fa";
    /**
     * 好友请求的相关json字段:标示好友请求方昵称
     */
    public static final String PUSH_ADD_FROM_NICK = "fn";
    /**
     * 好友请求的相关json字段:标示好友请求接收方objectId
     */
    public static final String PUSH_ADD_FROM_TOID = "tId";
    /**
     * 好友请求的相关json字段:标示该请求发送时间
     */
    public static final String PUSH_ADD_FROM_TIME = "ft";//请求发送时间

    /**
     * 已读回执的json字段：此条消息的接收方:toId、会话id、消息时间、回执消息的发送方name:fromusername
     */
    public static final String PUSH_READED_CONVERSIONID = "mId";//消息id--msgId
    public static final String PUSH_READED_FROM_ID = "fId";//回执消息的发送方id
    public static final String PUSH_READED_MSGTIME = "ft";//请求发送时间
    /**
     * 存放发送图片的目录
     */
    public static String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory() + "/bmobimdemo/image/";

    /**
     * 我的头像保存目录
     */
    public static String MyAvatarDir = "/sdcard/bmobimdemo/avatar/";
    /**
     * 拍照回调
     */
    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像

    public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
    public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
    public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
    public static final String EXTRA_STRING = "extra_string";


    public static final String ACTION_REGISTER_SUCCESS_FINISH = "register.success.finish";//注册成功之后登陆页面退出
}

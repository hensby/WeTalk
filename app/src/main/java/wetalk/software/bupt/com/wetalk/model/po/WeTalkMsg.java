package wetalk.software.bupt.com.wetalk.model.po;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import wetalk.software.bupt.com.wetalk.application.ChatManager;
import wetalk.software.bupt.com.wetalk.application.UserManager;
import wetalk.software.bupt.com.wetalk.util.JsonUtil;
import wetalk.software.bupt.com.wetalk.util.WeTalkConfig;
import wetalk.software.bupt.com.wetalk.util.WeTalkLog;
import wetalk.software.bupt.com.wetalk.util.WeTalkUtils;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkMsg extends WeTalkObject {
    private static final long serialVersionUID = 1L;

    private String tag;//标签-用于区分是聊天消息还是tag标签 

    /**
     * 该条消息所属的会话id：单聊模式下：会话id组成方式：发送者objectid+"&"+接收者objectid，群聊模式下：groudId
     */
    private String conversationId;
    /**
     * 消息具体内容：根据类型存放：TEXT-字符串文本，IMAGE-图片地址、地图-地理位置
     */
    private String content;// 

    /**
     * 接收者id--增加接收方id--V1.1.2
     */
    private String toId;

    /**
     * 消息是谁发送的ObjectId
     */
    private String belongId;// 

    /**
     * 发送者头像
     */
    private String belongAvatar;// 

    /**
     * 发送者昵称
     */
    private String belongNick;

    /**
     * 发送者userName-即Bmob账号
     */
    private String belongUsername;// 
    /**
     * 消息类型
     */
    private Integer msgType;
    /**
     * 发送时间
     */
    private String msgTime;// 
    /**
     * 标示该消息未读、已读状态
     */
    private Integer isReaded;// 
    /**
     * 该消息状态：发送成功、失败、已收到
     */
    private Integer status; // 

    /**
     * 额外字段
     */
    private String extra;//

    public WeTalkMsg(){

    }

    /**创建文本类型的BmobMsg
     * @param content
     * @param belongId
     * @param belongUsername
     * @param belongAvatar
     * @param belongNick
     * @param msgTime
     * @param msgType
     * @param isReaded
     * @param status
     */
    public WeTalkMsg(String tag,String conversationId, String content,String toId,String belongId,String belongUsername,String belongAvatar,String belongNick,String msgTime, Integer msgType, Integer isReaded, Integer status) {
        this.tag = tag;
        this.conversationId = conversationId;
        this.toId = toId;
        this.content = content;
        this.msgTime = msgTime;
        this.belongAvatar = belongAvatar;
        this.belongNick = belongNick;
        this.belongUsername = belongUsername;
        this.belongId = belongId;
        this.isReaded = isReaded;
        this.msgType = msgType;
        this.status = status;
    }

    /** 创建Tag标签的消息
     * @param tag
     * @param belongId
     * @param belongUsername
     * @param belongAvatar
     * @param belongNick
     * @param msgTime
     * @param isReaded：是否已读/未读
     * 为了避免接收不到Tag标签的消息，因此也要存放到后台
     */
    public WeTalkMsg(String tag, String toId,String belongId,String belongUsername,String belongAvatar,String belongNick,String msgTime, Integer isReaded){
        this.tag = tag;
        this.toId = toId;
        this.msgTime = msgTime;
        this.belongAvatar = belongAvatar;
        this.belongNick = belongNick;
        this.belongUsername = belongUsername;
        this.belongId = belongId;
        this.isReaded = isReaded;
    }

    /** 解析json-当同意对方好友请求时调用此方法生成临时会话
     * @Title: createReceiveMsg
     * @Description: TODO
     * @param  context
     * @param  json
     * @return WeTalkMsg
     * @throws
     */
    public static void createAndSaveRecentAfterAgree(Context context, String json){
        JSONObject jo;
        WeTalkMsg message=null;
        try {
            jo = new JSONObject(json);
            String tag = JsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TAG);
            //消息发送方的信息
            String targetId = JsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TARGETID);
            String username = JsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TARGETUSERNAME);
            String avatar = JsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_TARGETAVATAR);
            String nick = JsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_TARGETNICK);
            //增加消息接收方的ObjectId--目的是解决多账户登陆同一设备时，无法接到到非当前登陆用户的消息。
            String toId = JsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TOID);
            String msgTime = JsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_MSGTIME);
            String content = "我通过了你的好友验证请求，我们可以开始聊天了!";
            //登陆用户
            message = receiveMsg(context, tag,toId, content, targetId, username, avatar, nick, msgTime, WeTalkConfig.TYPE_TEXT);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            WeTalkLog.i("parseMessage错误："+e.getMessage());
        }
        if(message!=null){
            ChatManager.getInstance(context).saveReceiveMessage(false,message);
        }
    }

    /** 当推送消息接收不到的时候，采用此方法解析通过定时任务获取到的同意验证消息
     * @Title: createAndSaveRecentAfterAgree
     * @Description: TODO
     * @param  context
     * @param  msg
     * @throws
     */
    public static void createAndSaveRecentAfterAgree(Context context,WeTalkMsg msg){
        String content = "我通过了你的好友验证请求，我们可以开始聊天了!";
        //登陆用户
        WeTalkMsg message = receiveMsg(context, msg.getTag(), msg.getToId(), content,
                msg.getBelongId(), msg.getBelongUsername(), msg.getBelongAvatar(),
                msg.getBelongNick(), msg.getMsgTime(), WeTalkConfig.TYPE_TEXT);
        if(message!=null){
            ChatManager.getInstance(context).saveReceiveMessage(false,message);
        }
    }

    /**
     *组装成收到的消息
     */
    private static WeTalkMsg receiveMsg(Context context,String tag,String toId ,String content,String belongId ,
                                      String username,String avatar,String nick,String msgTime,Integer msgtype){
        WeTalkMsg message=null;
        //登陆用户
        ChatUser loginUser =  new ChatUser();

        if(loginUser!=null && loginUser.getUserID().equals(toId)){//是当前登陆用户的消息
            String loginId = String.valueOf(loginUser.getUserID());
            message = new WeTalkMsg(tag,belongId+"&"+loginId,
                    content,
                    loginId,
                    belongId,
                    username==null?"":username,
                    avatar==null?"":avatar,
                    nick==null?"":nick,
                    msgTime,
                    msgtype,
                    WeTalkConfig.STATE_UNREAD_RECEIVED,
                    WeTalkConfig.STATUS_SEND_RECEIVERED);
        }else{//不是当前登陆用户的消息-而是该设备其他登陆账号的信息
            message = new WeTalkMsg(tag,belongId+"&"+toId,
                    content,
                    toId,
                    belongId,
                    username==null?"":username,
                    avatar==null?"":avatar,
                    nick==null?"":nick,
                    msgTime,
                    msgtype,
                    WeTalkConfig.STATE_UNREAD_RECEIVED,
                    WeTalkConfig.STATUS_SEND_RECEIVERED);
        }
        return message;
    }

    /** 创建一条Tag标签的发送消息--用于存储到web端
     * @Title: createTagSendMsg
     * @Description: TODO
     * @param  context
     * @param  tag
     * @param  targetId
     * @param  currentUser
     * @return WeTalkMsg
     * @throws
     */
    @Deprecated
    public static WeTalkMsg createTagSendMsg(Context context,MsgTag tag, String targetId,ChatUser currentUser){
        String t="";
        if(tag == MsgTag.ADD_CONTACT){
            t = WeTalkConfig.TAG_ADD_CONTACT;
        }else if(tag==MsgTag.ADD_AGREE) {
            t=WeTalkConfig.TAG_ADD_AGREE;
        }
        ChatUser user = new ChatUser();
        WeTalkMsg msg = new WeTalkMsg(t,
                targetId,String.valueOf(user.getUserID()),
                user.getUserName(), user.getAvatar(),
                user.getNick(),
                String.valueOf(WeTalkUtils.getTimeStamp()),
                WeTalkConfig.INVITE_ADD_NO_VALIDATION);
        return msg;
    }

    /** 创建一条Tag标签的发送消息--用于存储到web端
     * @Title: createTagSendMsg
     * @Description: TODO
     * @param  context
     * @param  tag
     * @param  targetId
     * @param  currentUser
     * @return WeTalkMsg
     * @throws
     */
    public static WeTalkMsg createTagSendMsg(Context context,String tag, String targetId,ChatUser currentUser){
        ChatUser user =  new ChatUser();
        WeTalkMsg msg = new WeTalkMsg(tag, targetId,String.valueOf(user.getUserID()), user.getUserName(), user.getAvatar(), user.getNick(),
                String.valueOf(WeTalkUtils.getTimeStamp()),
                WeTalkConfig.INVITE_ADD_NO_VALIDATION);
        return msg;
    }

    /**
     * @Title: createLocationSendMsg
     * @Description: 创建一条位置类型的发送消息
     * @param @param context
     * @param @param receiptId
     * @param @param address
     * @param @param latitude
     * @param @param longtitude
     * @return WeTalkMsg
     * @throws
     */
    public static WeTalkMsg createLocationSendMsg(Context context,String receiptId,String address,double latitude,double longtitude){
        StringBuilder builder = new StringBuilder();
        builder.append(address).append("&").append(latitude).append("&").append(longtitude);
        return	createSendMessage(context, receiptId, builder.toString(), WeTalkConfig.STATUS_SEND_SUCCESS,WeTalkConfig.TYPE_LOCATION);
    }

    /**创建一条文本类型的发送消息
     * @Title: createSendMsg
     * @Description: TODO
     * @param  context
     * @param  receiptId 目标用户
     * @param  content
     * @return WeTalkMsg
     * @throws
     */
    public static WeTalkMsg createTextSendMsg(Context context,String receiptId,String content){
        return	createSendMessage(context, receiptId, content, WeTalkConfig.STATUS_SEND_SUCCESS,WeTalkConfig.TYPE_TEXT);
    }

    /**创建一条发送的消息
     * @Title: createMessage
     * @Description: TODO
     * @param  context
     * @param  receiptId
     * @param  content
     * @param  status
     * @param  msgtype
     * @return WeTalkMsg
     * @throws
     */
    public static WeTalkMsg createSendMessage(final Context context,final String receiptId,String content,Integer status,Integer msgtype){
        ChatUser loginUser =  new ChatUser();
        String loginId = String.valueOf(loginUser.getUserID());
        WeTalkMsg message = new WeTalkMsg("",loginId+"&"+receiptId,
                content,
                receiptId,
                loginId,
                loginUser.getUserName(),
                loginUser.getAvatar()==null ? "" :
                        loginUser.getAvatar(),
                loginUser.getNick(),
                String.valueOf(WeTalkUtils.getTimeStamp()),
                msgtype,
                WeTalkConfig.STATE_UNREAD,
                status);
        return message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getBelongAvatar() {
        return belongAvatar;
    }

    public void setBelongAvatar(String belongAvatar) {
        this.belongAvatar = belongAvatar;
    }

    public String getBelongNick() {
        return belongNick;
    }

    public void setBelongNick(String belongNick) {
        this.belongNick = belongNick;
    }

    public String getBelongUsername() {
        return belongUsername;
    }

    public void setBelongUsername(String belongUsername) {
        this.belongUsername = belongUsername;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(Integer isReaded) {
        this.isReaded = isReaded;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

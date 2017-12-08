package wetalk.software.bupt.com.wetalk.application;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import wetalk.software.bupt.com.wetalk.model.po.WeTalkConstant;
import wetalk.software.bupt.com.wetalk.model.po.WeTalkMsg;
import wetalk.software.bupt.com.wetalk.util.WeTalkConfig;
import wetalk.software.bupt.com.wetalk.util.WeTalkJsonUtil;
import wetalk.software.bupt.com.wetalk.util.WeTalkLog;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkInvitation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fromid;
    private String fromname;//来源
    private String avatar;//头像
    private String nick;
    private long time;//时间
    private int status;

    public WeTalkInvitation(String fromid, String fromname, String avatar, String nick, long time, int status){
        this.fromid =fromid;
        this.fromname =fromname;
        this.nick =nick;
        this.avatar =avatar;
        this.time =time;
        this.status =status;
    }

    /** 解析json-创建一个WeTalkMsg对象
     * @Title: createReceiveMsg
     * @Description: TODO
     * @param @param json
     * @param @return
     * @return WeTalkMsg
     * @throws
     */
    public static WeTalkInvitation createReceiverInvitation(String json){
        JSONObject jo;
        WeTalkInvitation message=null;
        try {
            jo = new JSONObject(json);
            //消息发送方的信息
            String targetId = WeTalkJsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TARGETID);
            String username = WeTalkJsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TARGETUSERNAME);
            String avatar = WeTalkJsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_TARGETAVATAR);
            String nick = WeTalkJsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_TARGETNICK);
            long time = WeTalkJsonUtil.getLong(jo,WeTalkConstant.PUSH_ADD_FROM_TIME);
            message = new WeTalkInvitation(targetId,username==null?"":username, avatar==null?"":avatar, nick==null?"":nick,time, WeTalkConfig.INVITE_ADD_NO_VALI_RECEIVED);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            WeTalkLog.i("parseMessage错误："+e.getMessage());
        }
        return message;
    }

    /** 从WeTalkMsg表中解析出Tag消息,本地标示为已收到，但未处理
     * @Title: createReceiverInvitation
     * @Description: TODO
     * @param @param msg
     * @param @return
     * @return WeTalkInvitation
     * @throws
     */
    public static WeTalkInvitation createReceiverInvitation(WeTalkMsg msg){
        WeTalkInvitation message = null;
        if(msg.getTag().equals(WeTalkConfig.TAG_ADD_CONTACT)){//添加好友请求
            String username = msg.getBelongUsername();
            String avatar = msg.getBelongAvatar();
            String nick = msg.getBelongNick();
            message = new WeTalkInvitation(msg.getBelongId(),username==null?"":username, avatar==null?"":avatar, nick==null?"":nick,Long.parseLong(msg.getMsgTime()), WeTalkConfig.INVITE_ADD_NO_VALI_RECEIVED);
        }
        return message;
    }

    public String getNick() {
        return nick;
    }


    public void setNick(String nick) {
        this.nick = nick;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

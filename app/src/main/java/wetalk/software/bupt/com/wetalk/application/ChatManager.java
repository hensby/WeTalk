package wetalk.software.bupt.com.wetalk.application;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import wetalk.software.bupt.com.wetalk.listener.FindListener;
import wetalk.software.bupt.com.wetalk.listener.PushListener;
import wetalk.software.bupt.com.wetalk.listener.SaveListener;
import wetalk.software.bupt.com.wetalk.listener.SwitchListener;
import wetalk.software.bupt.com.wetalk.listener.UploadFileListener;
import wetalk.software.bupt.com.wetalk.listener.UploadListener;
import wetalk.software.bupt.com.wetalk.model.po.ChatUser;
import wetalk.software.bupt.com.wetalk.model.po.MsgTag;
import wetalk.software.bupt.com.wetalk.model.po.WeTalkConstant;
import wetalk.software.bupt.com.wetalk.model.po.WeTalkFile;
import wetalk.software.bupt.com.wetalk.model.po.WeTalkMsg;
import wetalk.software.bupt.com.wetalk.util.WeTalkConfig;
import wetalk.software.bupt.com.wetalk.util.WeTalkLog;
import wetalk.software.bupt.com.wetalk.util.WeTalkNetUtil;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class ChatManager {
    //WeTalkPushManager<WeTalkChatInstallation> bmobPush;

    Context globalContext;
    // 创建private static类实例
    private volatile static ChatManager INSTANCE;
    // 同步锁
    private static Object INSTANCE_LOCK = new Object();

    /**
     * 使用单例模式创建--双重锁定
     */
    public static ChatManager getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (INSTANCE_LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new ChatManager();
                }
                INSTANCE.init(context);
            }
        return INSTANCE;
    }

    /**
     * 初始化
     * @Title: init
     * @Description: TODO
     * @param  context
     * @return void
     * @throws
     */
    public void init(Context context) {
        this.globalContext = context;
        //bmobPush = new WeTalkPushManager<WeTalkChatInstallation>(globalContext);
    }

    /**
     * 获取目标用户所绑定的设备id
     * @Title: getTargetUserInstallId
     * @Description: TODO先不实现
     * @param  objectId 目标用户的id
     * @throws
     */
    private void getTargetUserInstallId(String objectId,FindListener<ChatUser> findcallback) {
        /*
         *TODO query里面太复杂
         * WeTalkQuery<ChatUser> query = new WeTalkQuery<ChatUser>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(globalContext, findcallback);*/
    }

    /**允许开发者自定义提示语
     * @param msg
     * @param showName
     * @return
     */
    private JSONObject createSendMessage(final WeTalkMsg msg, String showName) {
        try {
            JSONObject json = new JSONObject();
            // 组装兼容ios的消息头
            JSONObject aps = new JSONObject();
            aps.put(WeTalkConstant.PUSH_BASE_SOUND, "default");
            String type = "";
            if (msg.getMsgType() == WeTalkConfig.TYPE_LOCATION) {
                type = "位置";
            } else if (msg.getMsgType() == WeTalkConfig.TYPE_IMAGE) {
                type = "图片";
            } else if (msg.getMsgType() == WeTalkConfig.TYPE_VOICE) {
                type = "语音";
            } else {
                type = "消息";
            }
            String alert = String.format("%1$s发来了一个"+type,msg.getBelongUsername());
            if(TextUtils.isEmpty(showName)){
                aps.put(WeTalkConstant.PUSH_BASE_ALERT, alert);
            }else{
                aps.put(WeTalkConstant.PUSH_BASE_ALERT, showName);
            }
            aps.put(WeTalkConstant.PUSH_BASE_BADGE, 1);
            json.put(WeTalkConstant.PUSH_BASE_APS, aps);
            // 普通消息不携带TAG
            String conversationId = msg.getConversationId();
            String toId = conversationId.split("&")[1];
            json.put(WeTalkConstant.PUSH_KEY_TAG, msg.getTag());
            json.put(WeTalkConstant.PUSH_KEY_TOID, toId);
            json.put(WeTalkConstant.PUSH_KEY_TARGETID, msg.getBelongId());
            json.put(WeTalkConstant.PUSH_KEY_MSGTYPE, msg.getMsgType());
            // 发送的内容
            json.put(WeTalkConstant.PUSH_KEY_CONTENT, msg.getContent());
            // 增加消息时间
            json.put(WeTalkConstant.PUSH_KEY_MSGTIME, msg.getMsgTime());
            // 额外字段
            json.put(WeTalkConstant.PUSH_KEY_EXTRA, msg.getExtra());
//			WeTalkLog.i("发送消息的Json：" + json.toString());
            return json;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**允许开发者自定义通知栏字段
     *
     * @param targetUser
     * @param msg
     * @param showAlert
     * @param pushcallback
     */
    public void sendTextMessage(final ChatUser targetUser,final WeTalkMsg msg, final String showAlert,final PushListener pushcallback){
        boolean isNetConnected = WeTalkNetUtil.isNetworkAvailable(globalContext);
        if (!isNetConnected) {// 若无网络
            uploadAndSaveMsg(false, targetUser, msg);
            pushcallback.onFailure(WeTalkConfig.CODE_NETWORK_ERROR, "网络连接失败，请检查网络!");
            return;
        }
        baseSendMessage(false, targetUser, msg,showAlert,pushcallback);
    }

    /**
     * 给指定objectId的用户发送文本消息，提供推送回调操作
     * @param  targetUser:当前的聊天用户
     * @param  msg 消息实体
     * @param  callback 推送成功与否的回调
     * @return void
     * @throws
     */
    public void sendTextMessage(final ChatUser targetUser,final WeTalkMsg msg, final PushListener pushcallback) {
        sendTextMessage(targetUser, msg, null, pushcallback);
    }

    /**
     * 给指定用户发送文本类型的消息且自定义通知栏：包括文字加表情和位置消息 默认推送成功之后存储消息到数据库（本地和WeTalk）
     * @Title: sendMessage
     * @Description: TODO
     * @param  content
     * @return
     * @throws
     */
    public void sendTextMessage(final ChatUser targetUser,final WeTalkMsg msg, final String showAlert) {
        sendTextMessage(targetUser, msg,showAlert,new PushListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTextMessage---> pushMessage:发送成功");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTextMessage---> 发送失败：code = "+arg0+",错误描述 = "+arg1);
            }
        });
    }

    /**
     * 给指定用户发送文本类型的消息：包括文字加表情和位置消息 默认推送成功之后存储消息到数据库（本地和WeTalk）
     * @Title: sendMessage
     * @Description: TODO
     * @param  content
     * @return
     * @throws
     */
    public void sendTextMessage(final ChatUser targetUser, final WeTalkMsg msg) {
        sendTextMessage(targetUser, msg, new PushListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTextMessage---> pushMessage:发送成功");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTextMessage---> 发送失败：code = "+arg0+",错误描述 = "+arg1);
            }
        });
    }

    /**
     * 重发文本消息
     * @Title: resendMessage
     * @Description: TODO
     * @param  targetUser
     * @param  msg
     * @return
     * @throws
     */
    public void resendTextMessage(final ChatUser targetUser,final WeTalkMsg msg, String showAlert,final PushListener pushcallback) {
        boolean isNetConnected = WeTalkNetUtil.isNetworkAvailable(globalContext);
        if (!isNetConnected) {// 若无网络
            uploadAndSaveMsg(false, targetUser, msg);
            pushcallback.onFailure(WeTalkConfig.CODE_NETWORK_ERROR, "网络连接失败，请检查网络!");
            return;
        }
        baseSendMessage(true, targetUser, msg, showAlert,pushcallback);
    }

    /**
     * 重发文本消息
     * @Title: resendMessage
     * @Description: TODO
     * @param  targetUser
     * @param  msg
     * @return
     * @throws
     */
    public void resendTextMessage(final ChatUser targetUser,final WeTalkMsg msg, final PushListener pushcallback) {
        resendTextMessage(targetUser, msg, null, pushcallback);
    }

    private void baseSendMessage(final boolean isResend,final ChatUser targetUser, final WeTalkMsg msg,final String showAlert,final PushListener pushcallback) {
        // 获取指定用户所绑定的设备
        getTargetUserInstallId(String.valueOf(targetUser.getUserID()),new FindListener<ChatUser>() {

            @Override
            public void onSuccess(List<ChatUser> arg0) {
                // TODO Auto-generated method stub
                // 默认获取第一顺位
                if (arg0 != null && arg0.size() > 0) {
                    // 获取用户绑定的设备id和设备类型
                    send(arg0.get(0), createSendMessage(msg,showAlert),new PushListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            // WeTalkLog.i("baseSendMessage---> pushMessage:发送成功");
                            msg.setStatus(WeTalkConfig.STATUS_SEND_SUCCESS);
                            uploadAndSaveMsg(true, targetUser,msg);
                            pushcallback.onSuccess();
                        }

                        @Override
                        public void onFailure(int arg0,
                                              String arg1) {
                            // TODO Auto-generated method stub
                            WeTalkLog.i("baseSendMessage---> pushMessage:发送失败"	+ arg1);
                            if (!isResend) {
                                uploadAndSaveMsg(false,targetUser, msg);
                            }
                            pushcallback.onFailure(arg0, arg1);
                        }
                    });
                } else {
                    if (!isResend) {
                        uploadAndSaveMsg(false, targetUser, msg);
                    }
                    // WeTalkLog.i("baseSendMessage---> onSuccess():未查询到指定id为"+targetUser.getObjectId()+"的用户");
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("resendMessage---> errorCode= " + arg0+ ",errorMsg = " + arg1);
                if (!isResend) {
                    uploadAndSaveMsg(false, targetUser, msg);
                }
                pushcallback.onFailure(arg0, arg1);
            }

        });
    }

    /**
     * 重发文件类型的消息：包括图片和语音
     * @Title: resendMessage
     * @Description: TODO
     * @param  targetUser
     * @param  msg
     * @param  showAlert:允许自定义通知提示
     * @param  uploadCallback
     * @return
     * @throws
     */
    public void resendFileMessage(final ChatUser targetUser,final WeTalkMsg msg,final String showAlert, final UploadListener uploadCallback) {
        uploadCallback.onStart(msg);
        boolean isNetConnected = WeTalkNetUtil.isNetworkAvailable(globalContext);
        if (!isNetConnected) {// 若无网络
            msg.setStatus(WeTalkConfig.STATUS_SEND_FAIL);
            // 保存消息到本地数据库
            saveSendMessage(targetUser, msg);
            uploadCallback.onFailure(WeTalkConfig.CODE_NETWORK_ERROR, "网络连接失败，请检查网络!");
        } else {
            String content = msg.getContent();
            String local = "";
            if (msg.getMsgType() == WeTalkConfig.TYPE_VOICE) {// 语音格式的字符串的组装格式:语音文件的本地地址&长度
                local = content.split("&")[0];
            } else if (msg.getMsgType() == WeTalkConfig.TYPE_IMAGE) {// 图片格式的字符串的组装格式:file:///图片文件的本地地址
                local = content.substring(8);
            }
            // 而后上传该图片
            uploadAction(local, msg, targetUser,showAlert,uploadCallback);
        }
    }

    public void resendFileMessage(final ChatUser targetUser,final WeTalkMsg msg, final UploadListener uploadCallback) {
        resendFileMessage(targetUser, msg, null, uploadCallback);
    }

    /**
     * 上传操作
     * @Title: uploadFile
     * @Description: TODO
     * @param  local
     * @param  msg
     * @param  targetUser
     * @param  uploadCallback
     * @return
     * @throws
     */
    private void uploadAction(final String local, final WeTalkMsg msg,final ChatUser targetUser, String showAlert,final UploadListener uploadCallback) {
        long start = System.currentTimeMillis();
        long length = new File(local).length();
//		WeTalkLog.i("uploadAction--->上传的文件大小:" + WeTalkUtils.getFormatSize(length));
        long size = length / 1024 / 1024;
        final WeTalkFile weTalkFile = new WeTalkFile(new File(local));
        if (size <= 5) {
            weTalkFile.uploadblock(globalContext, new NewUploadFileListener(start,local,
                    targetUser, msg, weTalkFile,showAlert, uploadCallback));
        } else {
            weTalkFile.uploadblock(globalContext, new NewUploadFileListener(start,
                    local, targetUser, msg, weTalkFile,showAlert,uploadCallback));
        }

    }

    /** 自定义的新的上传文件监听
     * @ClassName: NewUploadFileListener
     * @Description: TODO
     * @author smile
     * @date 2014-7-23 下午8:15:08
     */
    private class NewUploadFileListener extends UploadFileListener {
        WeTalkMsg msg;
        WeTalkFile bmobfile;
        ChatUser targetUser;
        UploadListener uploadCallback;
        String local;
        String showAlert;
//		long start ;

        public NewUploadFileListener(long start,String local, ChatUser targetUser,
                                     WeTalkMsg msg, WeTalkFile file, String showAlert,UploadListener uploadCallback) {
            this.local = local;
            this.showAlert = showAlert;
            this.msg = msg;
            this.bmobfile = file;
            this.targetUser = targetUser;
            this.uploadCallback = uploadCallback;
        }

        @Override
        public void onSuccess() {
            WeTalkLog.i("uploadAction---> 上传成功:" + bmobfile.getFileUrl(globalContext));
            switchLarget2Short(bmobfile.getFileUrl(globalContext), new SwitchListener() {

                @Override
                public void onSuccess(String shortUrl) {
                    // TODO Auto-generated method stub
//					 WeTalkLog.i("转换之后的短连接:" + shortUrl);
                    if (msg.getMsgType() == WeTalkConfig.TYPE_VOICE) {// 语音类型的话，组装格式：网络url&长度
                        String length = msg.getContent().split("&")[1];
                        msg.setContent(shortUrl + "&" + length);
                    } else {
                        msg.setContent(shortUrl);
                    }
                    sendTextMessage(targetUser, msg, showAlert,new PushListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            // 前面加上本地的录音地址
                            if (msg.getMsgType() == WeTalkConfig.TYPE_VOICE) {// 语音类型的话，组装格式：网络url&长度
                                msg.setContent(local + "&" + msg.getContent());
                            } else {// 始终存储到本地的是本地地址+"&"+网络地址
                                msg.setContent("file:///" + local + "&"
                                        + msg.getContent());
                            }
                            msg.setIsReaded(WeTalkConfig.STATE_UNREAD_RECEIVED);
                            //WeTalkDB.create(globalContext).saveMessage(msg);
                            uploadCallback.onSuccess();
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            uploadCallback.onFailure(arg0, arg1);
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    // TODO Auto-generated method stub
                    WeTalkLog.i("转换短连接出错:" + error);
                    uploadAndSaveMsg(false, targetUser, msg);
                    uploadCallback.onFailure(WeTalkConfig.CODE_COMMON_FAILURE,
                            error);
                }
            });

        }

        @Override
        public void onProgress(Integer arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onFailure(int arg0, String arg1) {
            // TODO Auto-generated method stub
            WeTalkLog.i("uploadAction---> 上传失败:" + arg1);
            uploadAndSaveMsg(false, targetUser, msg);
            uploadCallback.onFailure(arg0, arg1);
        }

    }

    /**发送图片:允许开发者自定义通知提示语以及增加额外推送字段
     * @param targetUser
     * @param localPath
     * @param extra
     * @param showAlert
     * @param uploadCallback
     */
    public void sendImageMessage(final ChatUser targetUser,final String localPath,String extra, String showAlert,final UploadListener uploadCallback){
        // 先构建一个可用于界面显示的msg对象：
        // 图片的话，刚开始存储的是本地地址（前面加file:///，用于显示本地图片），下载完成之后存储的是本地地址+"&"+网络后的地址,传送给对方的还是网络地址
        final WeTalkMsg msg = WeTalkMsg.createSendMessage(globalContext, String.valueOf(targetUser.getUserID()), "file:///" + localPath,WeTalkConfig.STATUS_SEND_START, WeTalkConfig.TYPE_IMAGE);
        if(extra!=null && !extra.equals("")){
            msg.setExtra(extra);
        }
        uploadCallback.onStart(msg);
        // 若无网络则存储的是本地的地址
        boolean isNetConnected = WeTalkNetUtil.isNetworkAvailable(globalContext);
        if (!isNetConnected) {// 若无网络
            uploadAndSaveMsg(false, targetUser, msg);
            uploadCallback.onFailure(WeTalkConfig.CODE_NETWORK_ERROR,"网络连接失败，请检查网络！");
        } else {
            // //而后上传该图片
            uploadAction(localPath, msg, targetUser, showAlert,uploadCallback);
        }
    }

    /**发送图片:允许开发者为推送增加额外字段
     * @param targetUser
     * @param localPath
     * @param extra
     * @param uploadCallback
     */
    public void sendImageMessage(final ChatUser targetUser,final String localPath,String extra, final UploadListener uploadCallback){
        sendImageMessage(targetUser, localPath, extra, null, uploadCallback);
    }

    /**
     * 发送图片
     * @Title: sendImageMessage
     * @Description: TODO
     * @param  targetUser :目标用户
     * @param  localPath：该图片的本地地址
     * @param  uploadCallback ：回调
     * @return void
     * @throws
     */
    public void sendImageMessage(final ChatUser targetUser,final String localPath, final UploadListener uploadCallback) {
        sendImageMessage(targetUser, localPath,null,uploadCallback);
    }

    /**
     * 发送语音消息:允许开发者自定义通知提示语
     * @Title: sendVoicemessage
     * @Description: TODO
     * @param  targetUser
     * @param  localPath
     * @param  length
     * @param  uploadCallback
     * @return void
     * @throws
     */
    public void sendVoiceMessage(final ChatUser targetUser,	final String localPath, int length,	String extra,String showAlert,final UploadListener uploadCallback) {
        // 先构建一个可用于界面显示的msg对象：
        // 语音的话：刚开始存储的是本地地址&长度，下载完成之后存储的是本地地址&网络地址&长度
        StringBuilder content = new StringBuilder();
        content.append(localPath).append("&").append(length);
        final WeTalkMsg msg = WeTalkMsg.createSendMessage(globalContext,
                String.valueOf(targetUser.getUserID()),content.toString(),
                WeTalkConfig.STATUS_SEND_START, WeTalkConfig.TYPE_VOICE);
        if(extra!=null && !extra.equals("")){
            msg.setExtra(extra);
        }
        uploadCallback.onStart(msg);
        // 若无网络则存储的是本地的地址
        boolean isNetConnected = WeTalkNetUtil.isNetworkAvailable(globalContext);
        if (!isNetConnected) {// 若无网络
            msg.setStatus(WeTalkConfig.STATUS_SEND_FAIL);
            // 保存消息到本地数据库
            saveSendMessage(targetUser, msg);
            uploadCallback.onFailure(WeTalkConfig.CODE_NETWORK_ERROR,"网络连接失败，请检查网络！");
        } else {
            // 而后上传该图片
            uploadAction(localPath, msg, targetUser,showAlert, uploadCallback);
        }
    }

    /**发送语音：允许开发者推送增加自定义字段
     * @param targetUser
     * @param localPath
     * @param length
     * @param extra
     * @param uploadCallback
     */
    public void sendVoiceMessage(final ChatUser targetUser,	final String localPath, int length,	String extra,final UploadListener uploadCallback) {
        sendVoiceMessage(targetUser, localPath, length, extra, null, uploadCallback);
    }


    /**发送语音
     * @param targetUser
     * @param localPath
     * @param length
     * @param uploadCallback
     */
    public void sendVoiceMessage(final ChatUser targetUser,	final String localPath, int length,	final UploadListener uploadCallback) {
        sendVoiceMessage(targetUser, localPath, length, null, uploadCallback);
    }

    /**
     * 上传服务器并保存到本地 uploadAndSaveMsg
     * @Title: uploadAndSaveMsg
     * @Description: TODO
     * @param  isSuccess
     * @param  targetUser
     * @param  msg
     * @return
     * @throws
     */
    private void uploadAndSaveMsg(boolean isSuccess, ChatUser targetUser,WeTalkMsg msg) {
        if (!isSuccess) {// 不成功
            msg.setStatus(WeTalkConfig.STATUS_SEND_FAIL);
        } else {
            msg.setStatus(WeTalkConfig.STATUS_SEND_SUCCESS);
            // 保存消息到服务器
            insertMessage(msg);
        }
        // 保存消息到本地数据库
        saveSendMessage(targetUser, msg);
    }

    /**
     * 发送消息时候,保存消息到本地数据库
     * @Title: saveSendMessage
     * @Description: TODO
     * @param  targetUser
     * @param  msg
     * @return
     * @throws
     */
    private void saveSendMessage(ChatUser targetUser, WeTalkMsg msg) {
        //发送的消息其都是已读状态的
        msg.setIsReaded(WeTalkConfig.STATE_READED);
        //保 存到本地消息表中
        /*WeTalkDB.create(globalContext).saveMessage(msg);
        // 保存到最近会话列表
        WeTalkRecent recent = new WeTalkRecent(targetUser.getObjectId(),
                targetUser.getUsername(), targetUser.getNick(),
                targetUser.getAvatar(), msg.getContent(),
                Long.parseLong(msg.getMsgTime()), msg.getMsgType());
        WeTalkDB.create(globalContext).saveRecent(recent);*/
    }

    /** 获取指定会话id和时间的消息
     * @Title: getMessage
     * @Description: TODO
     * @param  conversionId
     * @param  msgTime
     * @return WeTalkMsg
     * @throws
     */
    /*public WeTalkMsg getMessage(final String conversionId, final String msgTime){
        return WeTalkDB.create(globalContext).getMessage(conversionId, msgTime);
    }*/

    /**
     * 保存收到的消息到本地,并根据isAskReaded来判断是否发送已读回执
     * @Description: TODO
     * @param  isAskReaded：是否发送消息已读回执
     * @param  msg
     * @return
     * @throws
     */
    public void saveReceiveMessage(boolean isAskReaded, WeTalkMsg msg) {
        String convertId = msg.getConversationId();
        String toId = convertId.split("&")[1];// 这条消息是发送给谁的
        /*String loginid = UserManager.getInstance(globalContext).getCurrentUserObjectId();
        //收到的消息全部是未读已收到状态
        msg.setIsReaded(WeTalkConfig.STATE_UNREAD_RECEIVED);
        WeTalkDB.create(globalContext, toId).saveMessage(msg);
        // 保存到最近会话列表
        WeTalkRecent recent = new WeTalkRecent(msg.getBelongId(),
                msg.getBelongUsername(), msg.getBelongNick(),
                msg.getBelongAvatar(), msg.getContent(),
                Long.parseLong(msg.getMsgTime()), msg.getMsgType());
        WeTalkDB.create(globalContext, toId).saveRecent(recent);
        // 默认通知此消息所属方本人已阅读（指定会话id和时间）的消息的回执消息
        if (isAskReaded) {// 我要回给消息发送方一个已读的回执消息
            notifyTargetMsgReaded(msg.getBelongId(), loginid,msg.getConversationId(), msg.getMsgTime());
        }else{//不发送回执消息，也要更新WeTalkMsg表中的未读标示
            updateMsgReaded(true, msg.getBelongId(), msg.getMsgTime());
        }*/
    }

    /** 保存从定时任务中取到的好友请求到本地，同时更新后台的未读标示
     * @Title: saveReceiveInvites
     * @Description: TODO
     * @param
     * @return
     * @throws
     */
    public WeTalkInvitation saveReceiveInvite(WeTalkMsg msg){
        //保存好友请求消息
        WeTalkInvitation message =WeTalkInvitation.createReceiverInvitation(msg);
        //WeTalkDB.create(globalContext,msg.getToId()).saveInviteMessage(message);
        //已读消息发送成功之后更新该条消息的读取状态为已读
        updateMsgReaded(true,msg.getBelongId(), msg.getMsgTime());
        return message;
    }

    /** 保存从推送消息中取到的好友请求，更新后台的未读标示
     * @Title: saveReceiveInvite
     * @Description: TODO
     * @param  json
     * @param  toId
     * @return WeTalkInvitation
     * @throws
     */
    public WeTalkInvitation saveReceiveInvite(String json,String toId){
        WeTalkInvitation message =WeTalkInvitation.createReceiverInvitation(json);
        //保存好友请求消息
        //WeTalkDB.create(globalContext,toId).saveInviteMessage(message);
        //已读消息发送成功之后更新该条消息的读取状态为已读
        updateMsgReaded(true,message.getFromid(),String.valueOf(message.getTime()));
        return message;
    }

    /**
     * 发送消息已读回执
     * @Title: askMessageReaded
     * @Description: TODO
     * @param  to：已读回执接收方id
     * @param  conversionId 指定会话id
     * @param  msgTime 消息时间
     * @return
     * @throws
     */
    private void notifyTargetMsgReaded(final String toid, final String fromId,final String conversionId, final String msgTime) {
        // 获取指定用户所绑定的设备
        getTargetUserInstallId(toid, new FindListener<ChatUser>() {

            @Override
            public void onSuccess(List<ChatUser> arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null && arg0.size() > 0) {
                    // 获取用户绑定的设备id和设备类型
                    JSONObject jsontag = createReadedJson(toid, fromId,conversionId, msgTime);
                    send(arg0.get(0), jsontag, new PushListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            // WeTalkLog.i("notifyTargetMsgReaded--->pushMessage--> onSuccess");
                            //已读消息发送成功之后更新该条消息的读取状态为已读
                            updateMsgReaded(false,conversionId, msgTime);
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            WeTalkLog.i("notifyTargetMsgReaded--->pushMessage--> onFailure= "
                                    + arg0 + ",errorMsg = " + arg1);
                        }
                    });
                } else {
                    WeTalkLog.i("notifyTargetMsgReaded---> onSuccess():未查询到指定"
                            + toid + "的用户");
                }
            }


            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("notifyTargetMsgReaded---> errorCode= " + arg0
                        + ",errorMsg = " + arg1);
            }

        });
    }

    /**
     * 已读的消息回执
     * @Title: createReadedJson
     * @Description: TODO
     * @param  to
     * @param  conversionId
     * @param  msgTime
     * @return String
     * @throws
     */
    private JSONObject createReadedJson(String toid, String fromId,
                                        String conversionId, String msgTime) {
        try {
            JSONObject json = new JSONObject();
            JSONObject aps = new JSONObject();
            aps.put(WeTalkConstant.PUSH_BASE_SOUND, "");
            aps.put(WeTalkConstant.PUSH_BASE_ALERT, "");
            aps.put(WeTalkConstant.PUSH_BASE_BADGE, 0);
            json.put(WeTalkConstant.PUSH_BASE_APS, aps);

            json.put(WeTalkConstant.PUSH_KEY_TAG, WeTalkConfig.TAG_READED);
            json.put(WeTalkConstant.PUSH_ADD_FROM_TOID, toid);
            json.put(WeTalkConstant.PUSH_READED_FROM_ID, fromId);
            json.put(WeTalkConstant.PUSH_READED_CONVERSIONID, conversionId);
            json.put(WeTalkConstant.PUSH_READED_MSGTIME, msgTime);
//			WeTalkLog.i("已读回执的json：" + json.toString());
            return json;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新指定消息的发送状态
     * @Title: updateMsgStatus
     * @Description: TODO
     * @param  isSuccess
     * @param  targetUser
     * @param  msg
     * @return
     * @throws
     */
    public void updateMsgStatus(String conversionId, String msgTime) {
        //WeTalkDB.create(globalContext).updateTargetMsgStatus(WeTalkConfig.STATUS_SEND_RECEIVERED,conversionId, msgTime);
    }

    /**
     * 上传消息到消息表中
     * @Title: insertMessage
     * @Description: TODO
     * @param  msg
     * @return
     * @throws
     */
    private void insertMessage(final WeTalkMsg msg) {
        /*msg.save(globalContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
//				 WeTalkLog.i("保存到服务器成功："+msg.getObjectId());
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("保存到服务器失败：" + arg1);
            }
        });*/
    }

    /** 更新指定的WeTalkMsg的未读状态
     * @Title: setReadedForMsg
     * @Description: TODO
     * @param  msg
     * @param  value
     * @param  msgTime
     * @return
     * @throws
     */
    public void updateMsgReaded(boolean hasTag,final String value, final String msgTime){
        queryMsg(hasTag,value, msgTime,new FindListener<WeTalkMsg>() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("updateReaded查询消息失败："+arg1);
            }

            @Override
            public void onSuccess(final List<WeTalkMsg> arg0) {
                // TODO Auto-generated method stub
                if(arg0!=null && arg0.size()>0){
                    final WeTalkMsg message = new WeTalkMsg();
                    message.setIsReaded(WeTalkConfig.STATE_READED);
                    /*message.update(globalContext, arg0.get(0).getObjectId(), new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
//							WeTalkLog.i(arg0.get(0).getObjectId() +"已读状态更新成功");
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            WeTalkLog.i("已读状态更新失败：arg1"+arg1);
                        }
                    });*/
                }else{
                    WeTalkLog.i("未查询到指定的未读消息");
                }
            }
        });
    }

    /** 查询WeTalkMsg表中的特定数据：hasTag=false,表明查询的是聊天消息。否则就是Tag消息（添加好友、同意好友请求）
     * @Title: queryMsg
     * @Description: TODO
     * @param  hasTag
     * @param  value
     * @param  msgTime
     * @param  findCallback
     * @return
     * @throws
     */
    private void queryMsg(boolean hasTag,final String value, final String msgTime,FindListener<WeTalkMsg> findCallback){
        /*WeTalkQuery<WeTalkMsg> query = new WeTalkQuery<WeTalkMsg>();
        if(!hasTag){//是否是tag消息
            query.addWhereEqualTo("conversationId", value);
        }else{
            query.addWhereEqualTo("belongId", value);
        }
        query.addWhereEqualTo("msgTime", msgTime);
        query.findObjects(globalContext, findCallback);*/
    }


    /** 从WeTalkMsg表中查询特定的消息
     * @Title: queryTargetMsg
     * @Description: TODO
     * @param  fromId
     * @param  msgTime
     * @param  findCallBack
     * @return
     * @throws
     */
    public void queryTargetMsg(String fromId,String msgTime,FindListener<WeTalkMsg> findCallBack){
        //消息发送方的信息
        /*WeTalkQuery<WeTalkMsg> query = new WeTalkQuery<WeTalkMsg>();
        query.addWhereEqualTo("ObjectId", fromId);
        query.addWhereEqualTo("msgTime", msgTime);
        query.findObjects(globalContext,findCallBack);*/
    }

    /** 给指定用户发送Json格式的消息，提供回调方法--用于扩展MsgTag，发送自定义格式的消息，这个格式可以随便定义，只负责发送消息，不提供自定义格式的消息的处理
     * @Title: sendJsonMessage
     * @Description: TODO
     * @return
     * @throws
     */
    public void sendJsonMessage(final String json,final String targetId,final PushListener pushCallback){
        // 获取指定用户所绑定的设备
        getTargetUserInstallId(targetId, new FindListener<ChatUser>() {

            @Override
            public void onSuccess(List<ChatUser> arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null && arg0.size() > 0) {
                    JSONObject jsontag;
                    try {
                        jsontag = new JSONObject(json);
                        send(arg0.get(0), jsontag, pushCallback);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    WeTalkLog.i("sendJsonMessage---> onSuccess():未查询到指定"+ targetId + "的用户");
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendJsonMessage---> errorCode= " + arg0+ ",errorMsg = " + arg1);
            }

        });
    }

    /** 给指定用户发送json格式的消息--用于扩展MsgTag，发送自定义格式的消息
     * @Title: sendTagMessage
     * @Description: TODO
     * @param  json
     * @param  targetId
     * @return
     * @throws
     */
    public void sendJsonMessage(final String json,final String targetId){
        sendJsonMessage(json, targetId, new PushListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
//				 WeTalkLog.i("sendJsonMessage--> onSuccess");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendJsonMessage--> onFailure= "+ arg0 + ",errorMsg = " + arg1);
            }
        });
    }

    /**
     * 给指定用户推送Tag标记的消息：添加好友、添加好友请求已同意等类型的回执消息
     * @Title: sendTagMessage
     * @Description: TODO
     * @param  tag 消息类型
     * @param  objectid 该用户的objectid
     * @return
     * @throws
     */
    @Deprecated
    public void sendTagMessage(final MsgTag tag, final String targetId) {
        sendTagMessage(tag, targetId, new PushListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
//				 WeTalkLog.i("sendTagMessage--->pushMessage--> onSuccess");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTagMessage--->pushMessage--> onFailure= "+ arg0 + ",errorMsg = " + arg1);
            }
        });
    }

    /**
     * 给指定用户推送Tag标记的消息请提供回调操作：添加好友、添加好友请求已同意等类型的回执消息
     * @Title: sendTagMessage
     * @Description: TODO
     * @param  tag 消息类型
     * @param  installId 目标用户绑定的设备id
     * @param  pushCallback 发送回调
     * @return
     * @throws
     */
    @Deprecated
    public void sendTagMessage(final MsgTag tag, final String targetId,final PushListener pushCallback) {
        // 获取指定用户所绑定的设备
        getTargetUserInstallId(targetId, new FindListener<ChatUser>() {

            @Override
            public void onSuccess(List<ChatUser> arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null && arg0.size() > 0) {
                    /*final WeTalkMsg msg = WeTalkMsg.createTagSendMsg(globalContext, tag, targetId, WeTalkUserManager.getInstance(globalContext).getCurrentUser());
                    JSONObject jsontag = createTagMessage(msg);
                    send(arg0.get(0), jsontag, new PushListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            //将该Tag消息上传到后台
                            insertMessage(msg);
                            pushCallback.onSuccess();
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            pushCallback.onFailure(arg0, arg1);
                        }
                    });*/
                } else {
                    WeTalkLog.i("sendTagMessage---> onSuccess():未查询到指定"+ targetId + "的用户");
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTagMessage---> errorCode= " + arg0+ ",errorMsg = " + arg1);
            }

        });
    }

    /**
     * 给指定用户推送String类型Tag标记的消息:可扩展msgTag
     * @Title: sendTagMessage
     * @Description: TODO
     * @param  tag 消息类型
     * @param  targetId 目标用户id
     * @param  pushCallback 发送回调
     * @return
     * @throws
     */
    public void sendTagMessage(String tag, final String targetId) {
        sendTagMessage(tag, targetId, new PushListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
//				 WeTalkLog.i("sendTagMessage--->pushMessage--> onSuccess");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTagMessage--->pushMessage--> onFailure= "+ arg0 + ",errorMsg = " + arg1);
            }
        });
    }

    /**
     * 给指定用户推送Tag标记的消息请提供回调操作:此方法方便开发者使用自定义tag标记的消息，不携带回调方法
     * @Title: sendTagMessage
     * @Description: TODO
     * @param  tag 消息类型
     * @param  installId 目标用户绑定的设备id
     * @param  pushCallback 发送回调
     * @return
     * @throws
     */
    public void sendTagMessage(final String tag, final String targetId,final PushListener pushCallback) {
        // 获取指定用户所绑定的设备
        getTargetUserInstallId(targetId, new FindListener<ChatUser>() {

            @Override
            public void onSuccess(List<ChatUser> arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null && arg0.size() > 0) {
                    /*final WeTalkMsg msg = WeTalkMsg.createTagSendMsg(globalContext, tag, targetId, WeTalkUserManager.getInstance(globalContext).getCurrentUser());
                    JSONObject jsontag = createTagMessage(msg);
                    send(arg0.get(0), jsontag, new PushListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            //将该Tag消息上传到后台
                            insertMessage(msg);
                            pushCallback.onSuccess();
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            pushCallback.onFailure(arg0, arg1);
                        }
                    });*/
                } else {
                    WeTalkLog.i("sendTagMessage---> onSuccess():未查询到指定"+ targetId + "的用户");
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                WeTalkLog.i("sendTagMessage---> errorCode= " + arg0+ ",errorMsg = " + arg1);
            }

        });
    }

    /**
     * 根据设备类型推送消息到指定平台
     * @Title: send
     * @Description: TODO
     * @param  deviceType
     * @param  installationId
     * @param  json
     * @param  pushCallback
     * @return
     * @throws
     */
    private void send(ChatUser user, JSONObject json,PushListener pushCallback) {
        String installationId = user.getInstallId();
        String deviceType = user.getDeviceType();
        /*WeTalkQuery<WeTalkChatInstallation> query = WeTalkInstallation.getQuery();
        if (deviceType != null && deviceType.equals("ios")) {
            query.addWhereEqualTo("deviceToken", installationId);
        } else {
            query.addWhereEqualTo("installationId", installationId);
        }
        bmobPush.setQuery(query);
        bmobPush.pushMessage(json, pushCallback);*/
    }

    /**
     * 向指定用户发送tag请求
     * @Title: createTagMessage
     * @Description: TODO
     * @param  tag 消息类型
     * @param  currentUser
     * @return String
     * @throws
     */
    private JSONObject createTagMessage(WeTalkMsg msg) {
        try {
            // String msgTag="";
            JSONObject json = new JSONObject();
            JSONObject aps = new JSONObject();
            String alert = "";
            if (msg.getTag() == WeTalkConfig.TAG_ADD_CONTACT) {// 添加好友
                // msgTag = WeTalkConfig.TAG_ADD_CONTACT;
                alert = msg.getBelongUsername()+ "请求添加你好友!";
            } else if (msg.getTag() == WeTalkConfig.TAG_ADD_AGREE) {// 自己同意添加好友
                // msgTag = WeTalkConfig.TAG_ADD_AGREE;
                json.put(WeTalkConstant.PUSH_KEY_TAG, WeTalkConfig.TAG_ADD_AGREE);
                alert = msg.getBelongUsername() + "同意添加你为好友";
            }
            json.put(WeTalkConstant.PUSH_KEY_TAG, msg.getTag());
            aps.put(WeTalkConstant.PUSH_BASE_ALERT, alert);
            aps.put(WeTalkConstant.PUSH_BASE_SOUND, "");
            aps.put(WeTalkConstant.PUSH_BASE_BADGE, 0);
            json.put(WeTalkConstant.PUSH_BASE_APS, aps);

            json.put(WeTalkConstant.PUSH_ADD_FROM_TOID, msg.getToId());
            json.put(WeTalkConstant.PUSH_ADD_FROM_TIME,msg.getMsgTime());// 发送请求的时间
            json.put(WeTalkConstant.PUSH_ADD_ID, msg.getBelongId());
            json.put(WeTalkConstant.PUSH_ADD_FROM_AVATAR, msg.getBelongAvatar());
            json.put(WeTalkConstant.PUSH_ADD_FROM_NAME, msg.getBelongUsername());
            json.put(WeTalkConstant.PUSH_ADD_FROM_NICK, msg.getBelongNick());
//			WeTalkLog.i("-->请求组装过后的json："+json.toString());
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 长连接地址转短连接地址
     * @Title: switchLarget2Short
     * @Description: TODO
     * @param
     * @return
     * @throws
     */
    private void switchLarget2Short(String netUrl, final SwitchListener listener) {
        String url = "http://s.bmob.cn/create.php?url=" + netUrl;
        /*Volley.newRequestQueue(globalContext).add(
                new JsonObjectRequest(Request.Method.GET, url, "",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // TODO Auto-generated method stub
                                try {
                                    String shortUrl = response.getString("d");
                                    listener.onSuccess("http://s.bmob.cn/"+ shortUrl);
                                } catch (JSONException e) {
                                    listener.onError(e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        listener.onError(error.getMessage());
                    }
                }));*/
    }


    /** 创建收到的消息
     * @Description: TODO
     * @param
     * @return
     * @throws
     */
    /*public void createReceiveMsg(String json,final OnReceiveListener receiveCallBack){
        JSONObject jo;
        try {
            jo = new JSONObject(json);
            final String tag = WeTalkJsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TAG);
            //增加消息接收方的ObjectId--目的是解决多账户登陆同一设备时，无法接到到非当前登陆用户的消息。
            final String toId = WeTalkJsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TOID);
            final String fromId = WeTalkJsonUtil.getString(jo, WeTalkConstant.PUSH_KEY_TARGETID);
            final String msgTime = WeTalkJsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_MSGTIME);
            final String content = WeTalkJsonUtil.getString(jo,	WeTalkConstant.PUSH_KEY_CONTENT);
            final Integer msgtype = WeTalkJsonUtil.getInt(jo, WeTalkConstant.PUSH_KEY_MSGTYPE);
            //消息发送方的信息
            //1、如果是你的好友，从好友数据库中获取该用户的资料
            Map<String, ChatUser> map = WeTalkUtils.list2map(WeTalkDB.create(globalContext,toId).getContactList());
            ChatUser targetUser = map.get(fromId);
            if(targetUser!=null){
                checkWeTalkMsg(targetUser, tag, fromId, toId, content, msgTime, msgtype, receiveCallBack);
            }else{
                //2、陌生人--就去查询WeTalkMsg表里面的消息
                WeTalkLog.i("ObjectId为"+fromId+"的用户不是您的好友");
                WeTalkUserManager.getInstance(globalContext).queryUserById(fromId, new FindListener<ChatUser>() {

                    @Override
                    public void onError(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        receiveCallBack.onFailure(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(List<ChatUser> arg0) {
                        // TODO Auto-generated method stub
                        if(arg0!=null && arg0.size()>0){
                            ChatUser user = arg0.get(0);
                            checkWeTalkMsg(user, tag, fromId, toId, content, msgTime, msgtype, receiveCallBack);
                        }else{
                            receiveCallBack.onFailure(WeTalkConfig.CODE_COMMON_FAILURE, "未查询到发送方的信息!");
                        }
                    }
                });
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            WeTalkLog.i("parseMessage错误："+e.getMessage());
        }
    }

    private void checkWeTalkMsg(ChatUser user,String tag,String fromId,String toId,String content,String msgTime,Integer msgtype,OnReceiveListener receiveCallBack){
        String username = user.getUsername();
        String avatar = user.getAvatar();
        String nick = user.getNick();
        WeTalkMsg msg = new WeTalkMsg(tag,fromId+"&"+toId,content,toId,fromId,username==null?"":username, avatar==null?"":avatar,nick==null?"":nick,
                msgTime,msgtype,WeTalkConfig.STATE_UNREAD_RECEIVED,WeTalkConfig.STATUS_SEND_RECEIVERED);
        ChatUser currentUser = WeTalkUserManager.getInstance(globalContext).getCurrentUser();
        if(currentUser!=null){//当前设备没有账号在登陆
            if( msg.getToId().equals(currentUser.getObjectId())){//只有当toId和当前登陆用户相符合才继续向下传递和弹通知，防止别人恶意刷消息
                boolean isExist = WeTalkDB.create(globalContext).checkTargetMsgExist(msg.getConversationId(), msg.getMsgTime());
                if(!isExist){
                    //这里要判断下，取到的聊天消息是否存储过，因为存在这种情况，当推送链接没连上的时候，全部采用的是定点任务取到的，
                    //而当推送又连接到时候，那些离线消息本机就会接收到，这时候就应该考虑下
                    saveReceiveMessage(true,msg);
                    receiveCallBack.onSuccess(msg);
                }else{
                    receiveCallBack.onFailure(WeTalkConfig.CODE_COMMON_EXISTED, "该消息记录本地已存在");
                }
            }else{//当前登陆账号不是toId的情况下，也应该存储收到的消息。
                saveReceiveMessage(true,msg);
            }
        }else{//当之前的用户B注销登陆了，但又没有登陆其他设备，那么A再向B发的消息就应该存储到对应B账户的数据库中
            saveReceiveMessage(true,msg);
        }
    }

    /** 获取当前用户所有的未读消息个数
     * @Title: getAllUnReadCount
     * @Description: TODO
     * @return int
     * @throws
     */
    /*public int getAllUnReadCount(){
        return WeTalkDB.create(globalContext).getAllUnReadCount();
    }*/
}

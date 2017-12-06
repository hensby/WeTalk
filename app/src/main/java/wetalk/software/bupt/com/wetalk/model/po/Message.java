package wetalk.software.bupt.com.wetalk.model.po;

import java.util.Date;

/**
 * Created by liunan on 2017/12/4.
 */

//消息
public class Message {

    private int message_id;
    private int session_id;
    private int user_id;
    private int to_id;
    private String content;
    private int content_type;
    private int content_isRead;
    private int content_status;
    private int content_isRecall;
    private Date content_time;
    private  String remark;

    public Message(int message_id,int session_id,int user_id,int to_id,String content,int content_type,
                   int content_isRead,int content_status,int content_isRecall,Date content_time,String remark){
        this.message_id = message_id;
        this.session_id = session_id;
        this.user_id = user_id;
        this.to_id = to_id;
        this.content = content;
        this.content_type = content_type;
        this.content_isRead = content_isRead;
        this.content_status =content_status;
        this.content_isRecall = content_isRecall;
        this.content_time = content_time;
        this.remark = remark;

    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getContent_isRead() {
        return content_isRead;
    }

    public void setContent_isRead(int content_isRead) {
        this.content_isRead = content_isRead;
    }

    public int getContent_status() {
        return content_status;
    }

    public void setContent_status(int content_status) {
        this.content_status = content_status;
    }

    public int getContent_isRecall() {
        return content_isRecall;
    }

    public void setContent_isRecall(int content_isRecall) {
        this.content_isRecall = content_isRecall;
    }

    public Date getContent_time() {
        return content_time;
    }

    public void setContent_time(Date content_time) {
        this.content_time = content_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

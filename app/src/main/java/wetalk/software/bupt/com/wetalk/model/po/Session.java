package wetalk.software.bupt.com.wetalk.model.po;

/**
 * Created by liunan on 2017/12/4.
 */

public class Session {
    private int session_id;
    private int user_id;
    private int to_id;
    private int session_type;
    private  String remark;

    public  Session(int session_id,int user_id,int to_id,int session_type,String remark){
        this.session_id = session_id;
        this.user_id = user_id;
        this.to_id = to_id;
        this.session_type = session_type;
        this.remark = remark;
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

    public int getSession_type() {
        return session_type;
    }

    public void setSession_type(int session_type) {
        this.session_type = session_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

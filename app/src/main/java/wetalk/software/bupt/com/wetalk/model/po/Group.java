package wetalk.software.bupt.com.wetalk.model.po;

/**
 * Created by liunan on 2017/12/4.
 */

public class Group {
    private  int group_id;
    private String group_name;
    private  int user_id;

    public Group(int group_id, String group_name, int user_id) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

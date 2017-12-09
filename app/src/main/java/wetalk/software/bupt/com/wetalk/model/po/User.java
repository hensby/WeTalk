package wetalk.software.bupt.com.wetalk.model.po;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by ww on 2017/12/2.
 */

public class User implements Serializable{
    private Integer userID;
    private String userName;
    private String password;
    private String avatar;
    private Integer departmentID;
    private String phone;
    private String email;
    private int userHeader;

    public User(){
        this.userID = (int)(Math.random()*10+1);
        this.userName = "test";
        this.password = "test";
        this.avatar = "";
        this.departmentID = (int)(Math.random()*10+1);
        this.phone = "112233344";
        this.email = "test@qq.com";
    }
    public User(Integer userid,String username,String password,String avatar,Integer departmentid
            ,String phone,String email){
        this.userID = userid;
        this.userName = username;
        this.password = password;
        this.avatar = avatar;
        this.departmentID = departmentid;
        this.phone = phone;
        this.email = email;
    }

    public User(String userName, Integer departmentid, String phone, String email, int userHeader) {
        this.userName = userName;
        this.departmentID = departmentid;
        this.phone = phone;
        this.email = email;
        this.userHeader = userHeader;
    }

    public User(String username, String password){
        this.userName = username;
        this.password = password;
    }
    public User(String username, int userHeader) {
        this.userName = username;
        this.userHeader = userHeader;
    }

    public int getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(int userHeader) {
        this.userHeader = userHeader;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserId(Integer userid) {
        this.userID = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getDepartment_id() {
        return departmentID;
    }

    public void setDepartment_id(Integer departmentid) {
        this.departmentID = departmentid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userID=").append(userID);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", departmentID=").append(departmentID);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", userHeader=").append(userHeader);
        sb.append('}');
        return sb.toString();
    }

    public static <T> T getCurrentUser(Context context, Class<T> arg0) {
        return null;
    }
}


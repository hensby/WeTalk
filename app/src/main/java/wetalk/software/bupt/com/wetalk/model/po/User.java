package wetalk.software.bupt.com.wetalk.model.po;

/**
 * Created by ww on 2017/12/2.
 */

//用户
public class User {
    private Integer user_id;
    private String userName;
    private String password;
    private String avatar;
    private Integer department_id;//修改department表， department使用枚举型。
    private String phone;
    private String email;
    private int userHeader;
    private  String remark;

    public User(Integer user_id,String username,String password,String avatar,Integer department_id
            ,String phone,String email,int userHeader){
        this.user_id = user_id;
        this.userName = username;
        this.password = password;
        this.avatar = avatar;
        this.department_id = department_id;
        this.phone = phone;
        this.email = email;
        this.userHeader = userHeader;
    }

    //每个人定义的user不同，以下是可以让程序跑起来的
    public User(String userName, Integer department_id, String phone, String email, int userHeader) {
        this.userName = userName;
        this.department_id = department_id;
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

    public User(){

    }

    //以上为每个人定义的user

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
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

    public int getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(int userHeader) {
        this.userHeader = userHeader;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}


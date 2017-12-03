package wetalk.software.bupt.com.wetalk.model.dao;
import wetalk.software.bupt.com.wetalk.model.po.User;


public interface UserDao {

    void addUser(User user);//添加新用户

    void delteUserByName(String name);//删除用户

    void updateUserPwd(String name, String pass);//通过同户名修改密码

    User queryUserByName(String name);//通过用户名查找用户

    boolean isExistsUser(User user);//判断是否存在重复用户

    boolean isLoginSuccess(User user);
}

package wetalk.software.bupt.com.wetalk.model;

import wetalk.software.bupt.com.wetalk.model.po.User;

/**
 * 登录成功或失败的接口
 */
public interface OnLoginListener {
    //表示登录标签
    String TAG = "登录";

    /**
     * 登录成功，传递了用户信息
     *
     * @param user
     */
    void loginSuccess(User user);

    /**
     * 登录失败，传递了用户信息
     *
     * @param user
     */
    void loginFail(User user);
}

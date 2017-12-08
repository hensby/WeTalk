package wetalk.software.bupt.com.wetalk.model.impl;

import android.content.Context;
import android.content.Intent;

import wetalk.software.bupt.com.wetalk.model.UserModelInter;
import wetalk.software.bupt.com.wetalk.model.OnLoginListener;
import wetalk.software.bupt.com.wetalk.model.dao.UserDao;
import wetalk.software.bupt.com.wetalk.model.dao.impl.ImplUserDao;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.view.activity.LoginActivity;
import wetalk.software.bupt.com.wetalk.view.activity.MainActivity;


public class UserModelImp implements UserModelInter {
    //获取数据库管理类，对数据库进行操作
    private UserDao userDao;
    private Context context;
    public UserModelImp(Context context){
        userDao = new ImplUserDao(context);
        this.context=context;
    }

    /**
     * 注册逻辑实现
     * @param name     用户名
     * @param pass     用户密码
     * @param listener 回调事件，如果成功调用注册成功方法，失败调用注册失败方法
     */
//    @Override
//    public void register(String name, String pass, OnRegisterListener listener) {
//        //将用户名和密码封装到user中
//        User user = new User(name,pass);
//        //数据库不存在该用户注册成功，否则注册失败
//        if(userDao.isExistsUser(user)){
//            listener.registerFail(user);
//        }else{
//            userDao.addUser(user);
//            listener.registerSuccess(user);
//        }
//    }

    /**
     * 登录逻辑实现
     * @param name     用户名
     * @param pass     用户密码
     * @param listener 回调事件，如果成功调用登录成功方法，失败调用登录失败方法
     */
    @Override
    public void login(String name, String pass, OnLoginListener listener) {
        User user = new User(name,pass);
        //判断是否登录成功
        if(userDao.isLoginSuccessWithJson(user)){
            listener.loginSuccess(user);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }else{
            listener.loginFail(user);
        }
    }

}

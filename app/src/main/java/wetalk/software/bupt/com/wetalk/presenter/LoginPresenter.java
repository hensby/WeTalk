package wetalk.software.bupt.com.wetalk.presenter;

import android.content.Context;
import android.os.Handler;

import wetalk.software.bupt.com.wetalk.model.ModelInter;
import wetalk.software.bupt.com.wetalk.model.OnLoginListener;
import wetalk.software.bupt.com.wetalk.model.impl.ModelImp;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.view.viewinter.ViewInter;

/**
 * Created by ww on 2017/12/2.
 */

public class LoginPresenter {
    //view层的控件，对view层进行操作
    ViewInter viewInter;
    //模型层的控件,对model层进行操作
    ModelInter modelInter;

    public LoginPresenter(ViewInter viewInter, Context context) {
        this.viewInter = viewInter;
        modelInter = new ModelImp(context);
    }
    /**
     * 清除功能
     */
    public void clear() {
        viewInter.clearUserName();
        viewInter.clearUserPass();
    }

    /**
     * 登录功能，其基本实现和注册一样，只是模型层处理的逻辑不一样
     */
    public void login(){
        modelInter.login(viewInter.getUsername(), viewInter.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
               Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewInter.successHint(user,TAG);
                    }
                },1000);
                viewInter.successHint(user,TAG);
            }

            @Override
            public void loginFail(final User user) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewInter.failHint(user,TAG);
                    }
                },1000);
                viewInter.failHint(user,TAG);
            }
        });
    }
}


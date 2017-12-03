package wetalk.software.bupt.com.wetalk.application;

import android.app.Application;

/**
 * Created by zhangjie on 2017/11/28.
 */

public class CustomApplication extends Application{
    public static CustomApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static CustomApplication getInstance() {
        return mInstance;
    }
}

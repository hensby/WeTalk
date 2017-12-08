package wetalk.software.bupt.com.wetalk.listener;

/**
 * Created by zhangjie on 2017/12/6.
 */

public abstract interface SwitchListener {

    public abstract void onSuccess(String shortUrl);

    public abstract void onError(String error);

}

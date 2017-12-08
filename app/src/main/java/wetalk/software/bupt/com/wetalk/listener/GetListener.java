package wetalk.software.bupt.com.wetalk.listener;

/**
 * Created by zhangjie on 2017/12/6.
 */

public abstract class GetListener<T> extends AbsListener {
    public GetListener() {
    }

    public abstract void onSuccess(T var1);
}
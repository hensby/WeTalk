package wetalk.software.bupt.com.wetalk.listener;

/**
 * Created by zhangjie on 2017/12/6.
 */

public abstract class AbsListener extends AbsBaseListener {
    public AbsListener() {
    }

    public abstract void onFailure(int var1, String var2);
}
package wetalk.software.bupt.com.wetalk.listener;

/**
 * Created by zhangjie on 2017/12/7.
 */

public abstract class SaveListener extends AbsBaseListener{
    public SaveListener() {
    }

    public abstract void onSuccess();

    public abstract void onFailure(int var1, String var2);

    protected final void postFailure(int code, String msg) {
        this.onFailure(code, msg);
    }
}

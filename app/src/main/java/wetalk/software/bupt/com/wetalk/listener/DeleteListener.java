package wetalk.software.bupt.com.wetalk.listener;

/**
 * Created by zhangjie on 2017/12/6.
 */

public abstract class DeleteListener {
    public DeleteListener() {
    }

    public abstract void onSuccess();

    public abstract void onFailure(int var1, String var2);

    public void onStart() {
    }

    public void onFinish() {
    }
}

package wetalk.software.bupt.com.wetalk.listener;

import java.util.List;

/**
 * Created by zhangjie on 2017/12/6.
 */

public abstract class FindListener<T> extends AbsBaseListener {
    public FindListener() {
    }

    public abstract void onSuccess(List<T> var1);

    public abstract void onError(int var1, String var2);
}

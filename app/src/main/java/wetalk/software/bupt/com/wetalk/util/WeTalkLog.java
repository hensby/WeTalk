package wetalk.software.bupt.com.wetalk.util;

import android.util.Log;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkLog {
    public static final String Tag = "BmobChat";

    public static void v(String tag, String msg) {

            Log.v(tag, "-->"+msg);
    }

    public static void d(String tag, String msg) {

            Log.d(tag, "-->"+msg);
    }

    public static void i(String type, String msg) {

            Log.i(Tag,"-->("+type+")"+msg);
    }


    public static void w(String tag, String msg) {

            Log.w(tag, "-->"+msg);
    }

    public static void e(String tag, String msg) {

            Log.e(tag,"-->"+msg);
    }

    public static void i(String msg) {

            Log.i(Tag, "-->"+msg);
    }
}

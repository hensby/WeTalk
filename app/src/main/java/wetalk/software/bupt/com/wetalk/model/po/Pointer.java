package wetalk.software.bupt.com.wetalk.model.po;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import wetalk.software.bupt.com.wetalk.listener.GetListener;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class Pointer implements Serializable{
    private static final long serialVersionUID = -2906907910428442090L;
    private String __type = "Pointer";
    private String className;
    private String objectId;
    private static JSONObject params;
    //TODO:need implement
    //private static of requestCommand;
    //private static This rApi$6fc396a8;

    public Pointer() {
    }

    public Pointer(String className, String objectId) {
        this.setClassName(className);
        this.setObjectId(objectId);
    }

    public Pointer(Object value) {
        if(value instanceof User) {
            User value3 = (User)value;
            this.setClassName("_User");
            this.setObjectId(String.valueOf(value3.getUserID()));
        } else if(value instanceof WeTalkRole) {
            WeTalkRole value2 = (WeTalkRole)value;
            this.setClassName(WeTalkRole.tableName);
            this.setObjectId(value2.getObjectId());
        } else {
            if(value instanceof WeTalkObject) {
                WeTalkObject value1 = (WeTalkObject)value;
                this.setClassName(value1.getClass().getSimpleName());
                this.setObjectId(value1.getObjectId());
            }

        }
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public <T> void getObject(Context context, GetListener<T> listener) {
        if(TextUtils.isEmpty(this.objectId)) {
            Looper.prepare();
            listener.onFailure(9006, "objectId is null.");
            Looper.loop();
        } else {
            Class var3 = (Class)((ParameterizedType)listener.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];

            try {
                params = new JSONObject();
                if(!User.class.getSimpleName().equals(var3.getSimpleName()) && !User.class.equals(var3.getSuperclass())) {
                    params.put("c", var3.getSimpleName());
                } else {
                    params.put("c", "_User");
                }

                params.put("objectId", this.objectId);
            } catch (JSONException var5) {
                var5.printStackTrace();
            }

            HashMap var4;
            (var4 = new HashMap()).put("params", params);
            /*rApi$6fc396a8 = new This(context, 1, "api", "/8/find", var4);
            (requestCommand = of.I(context)).Code(rApi$6fc396a8);
            requestCommand.Code(var3, listener, false);*/
        }
    }
}

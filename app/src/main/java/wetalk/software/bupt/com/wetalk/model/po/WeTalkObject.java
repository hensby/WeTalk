package wetalk.software.bupt.com.wetalk.model.po;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkObject implements Serializable{
    private String tableName = "";
    private static JSONObject data;
    private String objectId;

    public WeTalkObject() {
        this.tableName = this.getClass().getSimpleName();
        data = new JSONObject();
    }

    public WeTalkObject(String tableName) {
        this.tableName = tableName;
        data = new JSONObject();
    }

    public String getObjectId() {
        return this.objectId;
    }
}

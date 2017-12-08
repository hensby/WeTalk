package wetalk.software.bupt.com.wetalk.model.po;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkRole extends WeTalkObject{
    public static String tableName = "_Role";
    private String name;
    private Relation J = new Relation();
    private Relation K = new Relation();

    public String getTableName() {
        return tableName;
    }

    public WeTalkRole(String name) {
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Relation getRoles() {
        return this.J;
    }

    public Relation getUsers() {
        return this.K;
    }
}

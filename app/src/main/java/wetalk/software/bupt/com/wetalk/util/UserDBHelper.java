package wetalk.software.bupt.com.wetalk.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class UserDBHelper extends SQLiteOpenHelper {

    //数据库名
    public static final String DBNAME = "users.db";

    //版本号
    public static final int VERSION = 1;

    //表名
    public static final String TABLENAME = "users";
    //列名
    public static final String COLNUMNAME = "username";
    //列名
    public static final String COLNUMPASS = "password";

    public UserDBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    /**
     * 该方法只执行一次，首次执行创建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLENAME + "(user_id integer primary key autoincrement," +
                "username varchar(20) not null," +
                "password varchar(20) not null," +
                "avator varchar(255) not null," +
                "department_id integer(20) not null" +
                "phone varchar(11) not null" +
                "email varchar(32) not null)";
        db.execSQL(sql);
    }

    /**
     * 版本更新调用该方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 还原旧版本
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}

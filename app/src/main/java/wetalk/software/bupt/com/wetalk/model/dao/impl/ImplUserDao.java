package wetalk.software.bupt.com.wetalk.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import wetalk.software.bupt.com.wetalk.model.dao.UserDao;
import wetalk.software.bupt.com.wetalk.model.po.User;
import wetalk.software.bupt.com.wetalk.util.UserDBHelper;

/**
 * Created by ww on 2017/12/2.
 */

public class ImplUserDao implements UserDao {
    private UserDBHelper dbHelper;
    private SQLiteDatabase database;
    private ContentValues values;
    private Context context;
     String Jsonusername;
     String Jsonpassword;
    public ImplUserDao(Context context) {
        dbHelper = new UserDBHelper(context);
        this.context = context;
    }

    /**
     * 添加新用户
     *
     * @param user 注册的用户信息
     */
    @Override
    public void addUser(User user) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "insert into users(uName,uPass) values(?,?)";
//        database.execSQL(sql,new Object[]{user.getUserName(),user.getUserPass()});

        //方法二：封装的api操作，直接操作方法即可
        values = new ContentValues();
        values.put(UserDBHelper.COLNUMNAME, user.getUserName());
        values.put(UserDBHelper.COLNUMPASS, user.getPassword());
        database.insert(UserDBHelper.TABLENAME, null, values);
        database.close();
    }

    /**
     * 根据名字删除用户
     *
     * @param name 要删除用户的名字
     */
    @Override
    public void delteUserByName(String name) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "delete from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        database.execSQL(sql,new Object[]{name});
        //方法二：封装的api操作，直接操作方法即可
        database.delete(UserDBHelper.TABLENAME, UserDBHelper.COLNUMNAME + " = ?", new String[]{name});
        database.close();
    }

    /**
     * 更新用户密码
     *
     * @param name 用户名
     * @param pass 用户密码
     */
    @Override
    public void updateUserPwd(String name, String pass) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "update " + DBHelper.TABLENAME + " set " + DBHelper.COLNUMPASS + " = ? where " + DBHelper.COLNUMNAME + " = ?";
//        database.execSQL(sql,new Object[]{pass,name});
        //方法二：封装的api操作，直接操作方法即可
        values = new ContentValues();
        values.put(UserDBHelper.COLNUMPASS, pass);
        database.update(UserDBHelper.TABLENAME, values, UserDBHelper.COLNUMNAME + " = ?", new String[]{pass});
        database.close();
    }

    /**
     * 通过用户名查找用户
     *
     * @param name
     */
    @Override
    public User queryUserByName(String name) {
        database = dbHelper.getReadableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{name});
        //方法二：封装的api操作，直接操作方法即可
        Cursor cursor = database.query(UserDBHelper.TABLENAME, null, UserDBHelper.COLNUMNAME + " = ?", null, null, null, null);
        User user = null;
        while (cursor.moveToNext()) {
            user = new User();
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserDBHelper.COLNUMNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserDBHelper.COLNUMPASS)));
        }
        cursor.close();

        return user;
    }

    /**
     * 判断是否存在该用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean isExistsUser(User user) {
        User isExit = queryUserByName(user.getUserName());
        return isExit == null ? false : true;
    }

    /**
     * 判断是否登录成功
     *
     * @param user 登录的用户信息
     * @return
     */
    @Override
    public boolean isLoginSuccess(User user) {
        database = dbHelper.getReadableDatabase();
        //方法一：sql语句操作
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ? and " + DBHelper.COLNUMPASS + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{user.getUserName(), user.getUserPass()});
        //方法二：封装的api操作，直接操作方法即可
        Cursor cursor = database.query(UserDBHelper.TABLENAME, null, UserDBHelper.COLNUMNAME + " = ? and "
                + UserDBHelper.COLNUMPASS + " = ?", new String[]{user.getUserName(), user.getPassword()}, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean isLoginSuccessWithJson(User user) {
        try {
            //InputStreamReader 将字节输入流转换为字符流
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("user.json"), "UTF-8");
            //包装字符流,将字符流放入缓存里
            BufferedReader br = new BufferedReader(isr);
            String line;
            //StringBuilder和StringBuffer功能类似,存储字符串
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                //append 被选元素的结尾(仍然在内部)插入指定内容,缓存的内容依次存放到builder中
                builder.append(line);
            }
            br.close();
            isr.close();
            //builder.toString()返回表示此序列中数据的字符串
            JSONObject root = new JSONObject(builder.toString());
             Jsonusername = root.getString("username");
            Jsonpassword = root.getString("password");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (user.getUserName().equals(Jsonusername) && user.getPassword().equals(Jsonpassword)){
            return true;
        }else{
            return false;
        }
    }

}


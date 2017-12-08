package wetalk.software.bupt.com.wetalk.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class RecentContacts {
    private int Contacts_avator;
//    private String Contacts_id;
    private String Contacts_name;
    private String Lastmessage;
    private long LastContacts_time;
    public RecentContacts(){

    }
    public RecentContacts(int Contacts_avator,String Contacts_name,String Lastmessage,long LastContacts_time){
        this.Contacts_avator = Contacts_avator;
//        this.Contacts_id = Contacts_id;
        this.Contacts_name = Contacts_name;
        this.Lastmessage = Lastmessage;
        this.LastContacts_time = LastContacts_time;
    }

    public int  getContacts_avator() {
        return Contacts_avator;
    }

    public void setContacts_avator(int contacts_avator) {
        Contacts_avator = contacts_avator;
    }

//    public String getContacts_id() {
//        return Contacts_id;
//    }
//
//    public void setContacts_id(String contacts_id) {
//        Contacts_id = contacts_id;
//    }

    public String getContacts_name() {
        return Contacts_name;
    }

    public void setContacts_name(String contacts_name) {
        Contacts_name = contacts_name;
    }

    public String getLastmessage() {
        return Lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        Lastmessage = lastmessage;
    }

    public long getLastContacts_time() {
        return LastContacts_time;
    }

    public void setLastContacts_time(long lastContacts_time) {
        LastContacts_time = lastContacts_time;
    }
}

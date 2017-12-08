package wetalk.software.bupt.com.wetalk.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class RecentContacts {
    private int contactsImgId;
    private String contactsName;
    private String lastMessage;
    private String lastContactsTime;

    public RecentContacts(int contactsImgId, String contactsName, String lastMessage, String lastContactsTime) {
        this.contactsImgId = contactsImgId;
        this.contactsName = contactsName;
        this.lastMessage = lastMessage;
        this.lastContactsTime = lastContactsTime;
    }

    public int getContactsImgId() {
        return contactsImgId;
    }

    public void setContactsImgId(int contactsImgId) {
        this.contactsImgId = contactsImgId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastContactsTime() {
        return lastContactsTime;
    }

    public void setLastContactsTime(String lastContactsTime) {
        this.lastContactsTime = lastContactsTime;
    }
}

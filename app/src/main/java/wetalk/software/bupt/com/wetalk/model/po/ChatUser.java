package wetalk.software.bupt.com.wetalk.model.po;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class ChatUser extends User{
    private static final long serialVersionUID = 1L;
    private String nick;//昵称
    private String avatar;//头像信息
    private Relation contacts;//好友联系人
    private String installId;//设备Id
    private String deviceType;//设备类型
    private Relation blacklist;//黑名单

//	private List<String> groups = new ArrayList<String>();//加入的群组名-这个群组对应的是Installation表里的channels字段
//
//
//	public List<String> getGroups() {
//		return groups;
//	}
//
//	public void setGroups(List<String> groups) {
//		this.groups = groups;
//	}
    public ChatUser(){

    }

    public ChatUser(Integer userid,String username,String password,String avatar,Integer departmentid
            ,String phone,String email){
        super(userid,username,password,avatar,departmentid,phone,email);
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Relation getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Relation blacklist) {
        this.blacklist = blacklist;
    }

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Relation getContacts() {
        return contacts;
    }

    public void setContacts(Relation contacts) {
        this.contacts = contacts;
    }
}

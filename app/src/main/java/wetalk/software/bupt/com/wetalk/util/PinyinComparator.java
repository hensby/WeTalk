package wetalk.software.bupt.com.wetalk.util;

import java.util.Comparator;

import wetalk.software.bupt.com.wetalk.model.po.User;

/**
 * Created by Administrator on 2017/11/30.
 */

public class PinyinComparator implements Comparator{
    @Override
    public int compare(Object arg0, Object arg1) {
        // 按照名字排序
        User user0 = (User) arg0;
        User user1 = (User) arg1;
        String catalog0 = "";
        String catalog1 = "";

        if (user0 != null && user0.getUserName() != null
                && user0.getUserName().length() > 1)
            catalog0 = PinYinUtil.converterToFirstSpell(user0.getUserName())
                    .substring(0, 1);

        if (user1 != null && user1.getUserName() != null
                && user1.getUserName().length() > 1)
            catalog1 = PinYinUtil.converterToFirstSpell(user1.getUserName())
                    .substring(0, 1);
        int flag = catalog0.compareTo(catalog1);
        return flag;

    }
}

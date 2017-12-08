package wetalk.software.bupt.com.wetalk.model.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class Relation implements Serializable{
    private static final long serialVersionUID = 7419229244419967901L;
    private String __op = "AddRelation";
    private List<Pointer> objects = new ArrayList();

    public Relation(Object value) {
        this.objects.add(new Pointer(value));
    }

    public Relation() {
    }

    public void add(Object value) {
        this.objects.add(new Pointer(value));
    }

    public void remove(Object value) {
        this.__op = "RemoveRelation";
        this.objects.add(new Pointer(value));
    }

    /** @deprecated */
    @Deprecated
    public void isRemove(boolean state) {
        if(state) {
            this.__op = "RemoveRelation";
        }

    }

    public String get__op() {
        return this.__op;
    }

    public List<Pointer> getObjects() {
        return this.objects;
    }

    public void setObjects(List<Pointer> objects) {
        this.objects = objects;
    }
}

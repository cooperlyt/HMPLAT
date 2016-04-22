package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cooper on 4/22/16.
 */
@Name("recordStoreBoxCover")
public class RecordStoreBoxCover {

    @In(create = true)
    private EntityManager ownerEntityManager;

    private String frame;

    private String cabinet;

    private String box;

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        if (frame == null || frame.trim().equals("") || !frame.equals(this.frame)){
            result = null;
        }
        this.frame = frame;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        if (cabinet == null || cabinet.trim().equals("") || !cabinet.equals(this.cabinet)){
            result = null;
        }
        this.cabinet = cabinet;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        if (box == null || box.trim().equals("") || !box.equals(this.box)){
            result = null;
        }
        this.box = box;
    }

    private List<RecordStore> result;


    public List<RecordStore> getRecordStoreList(){
        if (result == null){
            if (frame == null || frame.trim().equals("")
                    || box == null || box.trim().equals("")
                    || cabinet == null || cabinet.trim().equals("") ){
                result = new ArrayList<RecordStore>(0);
            }else{

                result = ownerEntityManager.createQuery("select distinct recordStore from RecordStore recordStore left join recordStore.businessFiles bf " +
                        "where bf.recordLocal.frame = :frame and bf.recordLocal.cabinet = :cabinet and bf.recordLocal.box = :box",RecordStore.class)
                        .setParameter("cabinet",cabinet).setParameter("frame",frame).setParameter("box",box)
                        .getResultList();
            }

        }
        return result;

    }

}

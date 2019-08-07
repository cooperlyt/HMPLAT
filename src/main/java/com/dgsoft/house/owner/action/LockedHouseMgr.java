package com.dgsoft.house.owner.action;

import cc.coopersoft.house.LockType;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.LockedHouse;
import com.dgsoft.house.owner.model.LockedHouseCancel;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import javax.persistence.Transient;
import java.util.*;

/**
 * Created by cooper on 10/31/15.
 */

@Name("lockedHouseMgr")
public class LockedHouseMgr {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In
    private AuthenticationInfo authInfo;

    private HouseInfo houseInfo;

    private List<LockedHouse> lockedHouses;

    private List<LockedHouseCancel> lockedHousesCancels;

    private String houseCode;

    private String lockedReason;

    private String selectLockedId;

    public String getSelectLockedId() {
        return selectLockedId;
    }

    public void setSelectLockedId(String selectLockedId) {
        this.selectLockedId = selectLockedId;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        Logging.getLog(getClass()).debug("setHouseCode" + houseCode);
        this.houseCode = houseCode;
    }

    public HouseInfo getHouseInfo() {
        initLocked();
        return houseInfo;
    }

    public void setHouseInfo(HouseInfo houseInfo) {
        this.houseInfo = houseInfo;
    }

    public List<LockedHouse> getLockedHouses() {

        initLocked();

        return lockedHouses;
    }


    public List<LockedHouseCancel> getLockedHousesCancels(){
        initLocked();
        return lockedHousesCancels;
    }


    public boolean isCodeDefined(){
        return houseCode != null && !houseCode.trim().equals("");
    }

    public String getLockedReason() {
        return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
        this.lockedReason = lockedReason;
    }

    @Transient
    public void lockHouse(){
        if (isCodeDefined()) {
            ownerEntityLoader.getEntityManager().persist(new LockedHouse(houseCode, LockType.HOUSE_LOCKED, lockedReason, authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), new Date()));
            ownerEntityLoader.getEntityManager().flush();
            lockedHouses = null;
            lockedReason = null;
        }
    }

    @Transient
    public void unLockAll(){
        if (isCodeDefined()) {
            for (LockedHouse lh : getLockedHouses()) {
                if (LockType.HOUSE_LOCKED.equals(lh.getType())) {
                    ownerEntityLoader.getEntityManager().persist(new LockedHouseCancel(lh.getHouseCode(),lh.getEmpCode(),lh.getEmpName(),new Date(),lh.getDescription(),lh.getType()));
                    ownerEntityLoader.getEntityManager().remove(lh);
                }
            }
            ownerEntityLoader.getEntityManager().flush();
            lockedHouses = null;
            lockedHousesCancels = null;
        }

    }

    @Transient
    public void unLock(){
        if (isCodeDefined()) {
            LockedHouse lh = ownerEntityLoader.getEntityManager().find(LockedHouse.class, selectLockedId);
            if (lh != null) {
                ownerEntityLoader.getEntityManager().persist(new LockedHouseCancel(lh.getHouseCode(),lh.getEmpCode(),lh.getEmpName(),new Date(),lh.getDescription(),lh.getType()));
                ownerEntityLoader.getEntityManager().remove(lh);
                ownerEntityLoader.getEntityManager().flush();
            }
            lockedHouses = null;
            lockedHousesCancels = null;
        }
    }

    private void initLocked(){
        if (isCodeDefined() && lockedHouses == null) {

                HouseRecord houseRecord = ownerEntityLoader.getEntityManager().find(HouseRecord.class, houseCode);
                if (houseRecord != null) {
                    houseInfo = houseRecord.getBusinessHouse();
                } else {
                    houseInfo = houseEntityLoader.getEntityManager().find(House.class, houseCode);
                }
                lockedHouses = ownerEntityLoader.getEntityManager().createQuery("select lh from LockedHouse lh where lh.houseCode = :houseCode", LockedHouse.class)
                        .setParameter("houseCode", houseCode).getResultList();
                Collections.sort(lockedHouses, new Comparator<LockedHouse>() {
                    @Override
                    public int compare(LockedHouse o1, LockedHouse o2) {
                        return o1.getLockedTime().compareTo(o2.getLockedTime());
                    }
                });
        }
        if (isCodeDefined() && lockedHousesCancels == null){
            lockedHousesCancels = ownerEntityLoader.getEntityManager().createQuery("select lc from LockedHouseCancel lc where lc.houseCode =:houseCode", LockedHouseCancel.class)
                    .setParameter("houseCode",houseCode).getResultList();
            Collections.sort(lockedHousesCancels,new Comparator<LockedHouseCancel>() {
                @Override
                public int compare(LockedHouseCancel o1, LockedHouseCancel o2) {
                    return o1.getCancelDate().compareTo(o2.getCancelDate());
                }
            });

        }
    }
}

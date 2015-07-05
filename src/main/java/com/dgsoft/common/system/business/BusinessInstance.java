package com.dgsoft.common.system.business;

import java.util.Date;
import java.util.EnumSet;

/**
 * Created by cooper on 6/27/15.
 */
public interface BusinessInstance {

    public enum BusinessSource {
        BIZ_CREATE, BIZ_AFTER_SAVE, BIZ_IMPORT
    }

    public enum BusinessType{
        NORMAL,MODIFY,CANCEL
    }

    //业务中 ， 完成 ， 中止 ， 挂起， 撤消， 修改 ， 修改中， 已完成但不生效（如已被解除抵押的抵押业务）
    public enum BusinessStatus {
        RUNNING, COMPLETE, ABORT, SUSPEND, CANCEL, MODIFY, MODIFYING, COMPLETE_CANCEL;

        public static EnumSet<BusinessStatus> runningStatus(){
            return EnumSet.of(RUNNING,SUSPEND,MODIFYING);
        }
    }


    public BusinessSource getSource();

    public void setSource(BusinessSource source);

    public BusinessType getType();

    public void setType(BusinessType type);

    public BusinessStatus getStatus();

    public void setStatus(BusinessStatus status);

    public Date getCreateTime();

    public void setCreateTime(Date createTime);

    public String getDefineName();

    public void setDefineName(String defineName);

    public String getDefineId();

    public void setDefineId(String defineId);

    public String getMemo();

    public void setMemo(String memo);
}
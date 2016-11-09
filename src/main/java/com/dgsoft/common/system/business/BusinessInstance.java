package com.dgsoft.common.system.business;

import java.util.Date;
import java.util.EnumSet;

/**
 * Created by cooper on 6/27/15.
 */
public interface BusinessInstance {

        //业务建立， 补录， 导入， 外部程序（如：网签）
    public enum BusinessSource {
        BIZ_CREATE, BIZ_AFTER_SAVE, BIZ_IMPORT, BIZ_OUTSIDE
    }

    public enum BusinessType{
        NORMAL_BIZ,MODIFY_BIZ,CANCEL_BIZ
    }

    //业务中 ， 完成 ， 中止 ， 挂起， 撤消， 修改中, 已解除
    public enum BusinessStatus {
        RUNNING, COMPLETE, ABORT, SUSPEND, CANCEL, MODIFYING, COMPLETE_CANCEL;

        public static EnumSet<BusinessStatus> runningStatus(){
            return EnumSet.of(RUNNING,SUSPEND,MODIFYING);
        }
    }

    //不能撤回更正业务， 撤回业务要递归selectBiz设置状态

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

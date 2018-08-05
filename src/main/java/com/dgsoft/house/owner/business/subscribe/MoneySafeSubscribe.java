package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneySafe;
import com.dgsoft.common.system.RunParam;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wxy on 2018-08-05.
 */
@Name("moneySafeSubscribe")
public class MoneySafeSubscribe extends OwnerEntityHome<MoneySafe> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    private boolean moneySafeShow;

    public boolean isMoneySafeShow() {
        String systemDateStr = RunParam.instance().getStringParamValue("MONEY_SAFE_TIME");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date systemDate = null;
        try {
            systemDate = sdf.parse(systemDateStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Date nowDate = new Date();
        //有受理时间先以受理时间为准，没有受理时间（新建立），以当前时间new date()为准
        if (ownerBusinessHome.getInstance().getCreateTime()!=null && !ownerBusinessHome.getInstance().getCreateTime().equals("") ){
            nowDate = ownerBusinessHome.getInstance().getCreateTime();
        }

        //1.使用Date的compareTo()方法，大于、等于、小于分别返回1、0、-1
        if (nowDate.compareTo(systemDate)>=0){
            moneySafeShow = true;
        }else {
            moneySafeShow = false;
        }
        return moneySafeShow;
    }

    public void setMoneySafeShow(boolean moneySafeShow) {
        this.moneySafeShow = moneySafeShow;
    }

    @Override
    public void create(){
        if (isMoneySafeShow()) {
            super.create();
            if (ownerBusinessHome.getInstance().getBusinessProject().getMoneySafe() != null) {
                if (ownerBusinessHome.getInstance().getBusinessProject().getMoneySafe().getId() == null) {
                    setInstance(ownerBusinessHome.getInstance().getBusinessProject().getMoneySafe());
                } else {
                    setId(ownerBusinessHome.getInstance().getBusinessProject().getMoneySafe().getId());
                }
            } else {
                getInstance().setBusinessProject(ownerBusinessHome.getInstance().getBusinessProject());
                ownerBusinessHome.getInstance().getBusinessProject().setMoneySafe(getInstance());
            }
        }
    }








}

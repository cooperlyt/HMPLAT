package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Created by cooper on 7/6/15.
 */
@Name("ownerBusinessList")
public class OwnerBusinessList extends OwnerEntityQuery<OwnerBusiness>{


    private static final String EJBQL = "select business from OwnerBusiness business";

    private static final String[] RESTRICTIONS = {
            "business.status in (#{runBusinessList.runningStatus}))",
            "lower(business.id) = #{runBusinessList.businessId})"
    };

    private String businessId;

    public OwnerBusinessList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setOrderColumn("business.createTime");
        setOrderDirection("desc");
        setMaxResults(25);
    }

    public EnumSet<BusinessInstance.BusinessStatus> getRunningStatus(){
        return BusinessInstance.BusinessStatus.runningStatus();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


}

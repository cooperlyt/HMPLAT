package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.action.HouseBusinessCondition;
import com.dgsoft.house.owner.action.HouseBusinessSearch;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessModifyStart")
public class HouseBusinessModifyStart extends HouseBusinessSearch {

    public HouseBusinessModifyStart(){
        super();
        setEjbql(houseBusinessCondition.SHORT_EJBQL);
    }

    @RequestParameter
    private String businessDefineId;

    public String getBusinessDefineId() {
        return businessDefineId;
    }

    public void setBusinessDefineId(String businessDefineId) {
        this.businessDefineId = businessDefineId;
    }

    @In(create = true)
    private HouseBusinessCondition houseBusinessCondition;

    @Override
    protected RestrictionGroup getUseRestrictionGroup() {

        return houseBusinessCondition.getRestrictionGroup();
    }

    @Override
    protected String getUseEjbql() {
        String result = houseBusinessCondition.getEjbql();
        return result + " where biz.defineId = #{houseBusinessModifyStart.businessDefineId} and biz.status = 'COMPLETE' and biz.type in ('NORMAL_BIZ','MODIFY_BIZ')";
    }
}

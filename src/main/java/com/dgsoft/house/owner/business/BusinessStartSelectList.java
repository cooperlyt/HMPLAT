package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.action.HouseBusinessCondition;
import com.dgsoft.house.owner.action.HouseBusinessSearch;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/29/15.
 */
@Name("businessStartSelectList")
public class BusinessStartSelectList extends HouseBusinessSearch {

    public BusinessStartSelectList() {
        super();
        //setEjbql(HouseBusinessCondition.SHORT_EJBQL);
    }

    @In(create = true)
    private HouseBusinessCondition houseBusinessCondition;

    @In
    private BusinessDefineHome businessDefineHome;

    @Override
    public void validate(){
        searchAction();
    }


    @Override
    protected RestrictionGroup getUseRestrictionGroup() {
        return houseBusinessCondition.getRestrictionGroup();
    }

    @Override
    protected String getUseEjbql() {
        String result = houseBusinessCondition.getEjbql();
        String defineIdCondition = null;
        for(String defineId: businessDefineHome.getInstance().getPickBusinessDefineId().split(",")){
            if (defineIdCondition == null){
                defineIdCondition = "(";
            }else{
                defineIdCondition += ",";
            }
            defineIdCondition += "'"+ defineId.trim() + "'" ;
        }

        defineIdCondition += ")";

        return result + " left join ob.subStatuses subStatus where (ob.defineId in " + defineIdCondition + "  or (subStatus.status = 'COMPLETE' and subStatus.defineId in " + defineIdCondition + ")) and ob.status = 'COMPLETE' and ob.type in ('NORMAL_BIZ','MODIFY_BIZ')";

    }
}

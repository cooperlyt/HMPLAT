package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.action.HouseBusinessCondition;
import com.dgsoft.house.owner.action.HouseBusinessSearch;
import com.dgsoft.house.owner.action.MoneyBusinessCondition;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-04-18.
 */
@Name("moneyStartSelectList")
public class MoneyStartSelectList extends HouseBusinessSearch {

    public MoneyStartSelectList() {
        super();
        //setEjbql(HouseBusinessCondition.SHORT_EJBQL);
    }

    @In(create = true)
    private MoneyBusinessCondition moneyBusinessCondition;

    @In
    private BusinessDefineHome businessDefineHome;

    @Override
    public void validate(){
        searchAction();
    }


    @Override
    protected RestrictionGroup getUseRestrictionGroup() {
        return moneyBusinessCondition.getRestrictionGroup();
    }

    @Override
    protected String getUseEjbql() {
        String result = moneyBusinessCondition.getEjbql();
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

        return result + " where ob.defineId in " + defineIdCondition + " and ob.status <> 'RUNNING' and ob.status <> 'MODIFYING' and ob.status <> 'SUSPEND' and ob.type in ('NORMAL_BIZ','MODIFY_BIZ')";

    }
}

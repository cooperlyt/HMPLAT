package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.action.HouseBusinessCondition;
import com.dgsoft.house.owner.action.HouseBusinessSearch;
import com.dgsoft.house.owner.action.MoneyBackBusinessCondition;
import com.dgsoft.house.owner.action.MoneyBusinessCondition;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

/**
 * Created by wxy on 2019-08-17.
 */
@Name("moneyBackBusinessStartSelectList")
public class MoneyBackBusinessStartSelectList extends HouseBusinessSearch {

    public MoneyBackBusinessStartSelectList() {
        super();
    }

    @In(create = true)
    private MoneyBackBusinessCondition moneyBackBusinessCondition;

    @In
    private BusinessDefineHome businessDefineHome;

    @Override
    public void validate(){
        searchAction();
    }


    @Override
    protected RestrictionGroup getUseRestrictionGroup() {
        return moneyBackBusinessCondition.getRestrictionGroup();
    }

    @Override
    protected String getUseEjbql() {
        String result = moneyBackBusinessCondition.getEjbql();
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
        if (moneyBackBusinessCondition.getSearchType().equals(MoneyBusinessCondition.SearchType.HOUSE_OWNER) || moneyBackBusinessCondition.getSearchType().equals(MoneyBusinessCondition.SearchType.PERSON)){
            return result + " where owner.old = false and ob.defineId in " + defineIdCondition + " and ob.status <> 'RUNNING' and ob.status <> 'MODIFYING' and ob.status <> 'SUSPEND' and ob.type in ('NORMAL_BIZ','MODIFY_BIZ') and mb.status<>'CHANGED'";
        }else{
            return result + " left join ob.subStatuses subStatus where (ob.defineId in " + defineIdCondition + "  or (subStatus.status = 'ABORT' and subStatus.defineId in " + defineIdCondition + ")) and ob.status = 'ABORT' and ob.type in ('NORMAL_BIZ','MODIFY_BIZ')";

        }



    }
}

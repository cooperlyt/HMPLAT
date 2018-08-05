package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.action.HouseBusinessCondition;
import com.dgsoft.house.owner.action.HouseBusinessSearch;
import com.dgsoft.house.owner.action.MoneyBusinessCondition;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

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
        if (moneyBusinessCondition.getSearchType().equals(MoneyBusinessCondition.SearchType.HOUSE_OWNER) || moneyBusinessCondition.getSearchType().equals(MoneyBusinessCondition.SearchType.PERSON)){
            Logging.getLog(getClass()).debug("22222--业务查询");
            return result + " where owner.old = false and ob.defineId in " + defineIdCondition + " and ob.status <> 'RUNNING' and ob.status <> 'MODIFYING' and ob.status <> 'SUSPEND' and ob.type in ('NORMAL_BIZ','MODIFY_BIZ') and mb.status<>'CHANGED'";
        }else{
            Logging.getLog(getClass()).debug("111111--受理查询");
            return result + " where ob.defineId in " + defineIdCondition + " and ob.status <> 'RUNNING' and ob.status <> 'MODIFYING' and ob.status <> 'SUSPEND' and ob.type in ('NORMAL_BIZ','MODIFY_BIZ') and mb.status<>'CHANGED'";
        }



    }
}

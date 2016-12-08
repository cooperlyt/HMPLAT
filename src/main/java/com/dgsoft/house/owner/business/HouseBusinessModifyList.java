package com.dgsoft.house.owner.business;


import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.action.HouseBusinessCondition;
import com.dgsoft.house.owner.action.HouseBusinessSearch;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 7/31/15.
 */
@Name("houseBusinessModifyList")
public class HouseBusinessModifyList extends HouseBusinessSearch {

    public HouseBusinessModifyList(){
        super();
       // setEjbql(HouseBusinessCondition.SHORT_EJBQL);
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
        return result + " where biz.defineId = #{businessDefineHome.instance.id} and biz.status = 'COMPLETE' and biz.type in ('NORMAL_BIZ','MODIFY_BIZ')";
    }
}

package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 8/5/15.
 */
@Name("employeeBusinessOperList")
public class EmployeeBusinessOperList extends OwnerEntityQuery<TaskOper> {

    private static final int PAGE_ITEM_COUNT = 30;

    private static final String EJBQL = "select taskOper from TaskOper taskOper " +
            "left join fetch taskOper.ownerBusiness business";

    private static final String[] RESTRICTIONS = {
            "taskOper.empCode = #{credentials.username}"
    };

    public EmployeeBusinessOperList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setRestrictionLogicOperator("or");
        setOrderColumn("taskOper.operTime");
        setOrderDirection("desc");
        setMaxResults(PAGE_ITEM_COUNT);
    }

    public void more(){
        setMaxResults(getMaxResults() + PAGE_ITEM_COUNT);
    }
}

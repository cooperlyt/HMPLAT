package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.house.owner.model.BusinessProject;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/12/2016.
 */
@Name("projectBusinessList")
public class ProjectBusinessList extends MultiOperatorEntityQuery<BusinessProject> {

    public ProjectBusinessList() {
        setRestrictionLogicOperator("and");
        setOrderColumn(SortCol.ORDER_BY_CREATE_TIME.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }

    public enum SortCol{
        ORDER_BY_CREATE_TIME("ob.createTime"),
        ORDER_BY_BUSINESS_NAME("ob.defineName");


        private String colPath;

        SortCol(String colPath) {
            this.colPath = colPath;
        }

        public String getColPath() {
            return colPath;
        }
    }

    public SortCol[] getAllSortCol(){
        return SortCol.values();
    }

    public SortCol getSortCol(){
        for(SortCol col: SortCol.values()){
            if (getOrderColumn().equals(col.colPath)){
                return col;
            }
        }
        return null;
    }


}

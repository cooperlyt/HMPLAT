package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cooper on 7/31/15.
 */
public abstract class HouseBusinessSearch  extends MultiOperatorEntityQuery<OwnerBusiness> {


    protected abstract RestrictionGroup getUseRestrictionGroup();

    protected abstract String getUseEjbql();

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }

    public void resetPage(){
        setFirstResult(0);
    }

    private void resetEjbql(String sql){
        if (!getEjbql().equals(sql)){
            setEjbql(sql);
        }
    }

    public void resetCondition(){
        if ((getFirstResult() == null) || getFirstResult().intValue() ==0)
            resetPage();
    }


    public List<OwnerBusiness> getSearchResult(){
        resetEjbql(getUseEjbql());
        setRestrictionGroup(getUseRestrictionGroup());
        return getResultList();
    }

    public Long getResultCount(){
        resetEjbql(getUseEjbql());
        setRestrictionGroup(getUseRestrictionGroup());
        return super.getResultCount();
    }

    public enum SortCol{
        ORDER_BY_CREATE_TIME("biz.createTime"),
        ORDER_BY_BUSINESS_NAME("biz.defineName");


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


    public HouseBusinessSearch() {
        //setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setRestrictionLogicOperator("and");
        setOrderColumn(SortCol.ORDER_BY_CREATE_TIME.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }
}

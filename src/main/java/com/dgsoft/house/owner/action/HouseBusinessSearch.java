package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.HouseBusiness;

import java.util.List;

/**
 * Created by cooper on 7/31/15.
 */
public abstract class HouseBusinessSearch  extends MultiOperatorEntityQuery<HouseBusiness> {


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
        resetPage();
    }


    public List<HouseBusiness> getSearchResult(){
        resetEjbql(getUseEjbql());
        setRestrictionGroup(getUseRestrictionGroup());
        return getResultList();
    }

    public enum SortCol{
        ORDER_BY_CREATE_TIME("houseBusiness.ownerBusiness.createTime"),
        ORDER_BY_BUSINESS_NAME("houseBusiness.ownerBusiness.defineName"),
        ORDER_BY_PROJECT_NAME("houseBusiness.afterBusinessHouse.projectCode"),
        ORDER_BY_BUILD_NUMBER("houseBusiness.afterBusinessHouse.buildCode"),
        ORDER_BY_HOUSE_ORDER("houseBusiness.afterBusinessHouse.houseOrder"),
        ORDER_BY_MAIN_OWNER("houseBusiness.afterBusinessHouse.businessHouseOwner.personName");

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
        setMaxResults(25);
    }
}

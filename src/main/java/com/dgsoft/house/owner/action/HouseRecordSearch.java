package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.List;

/**
 * Created by cooper on 10/3/15.
 */
@Name("houseRecordSearch")
public class HouseRecordSearch extends MultiOperatorEntityQuery<HouseRecord> {

    public enum SortCol{
        HouseRecord_CREATE("biz.createTime"),
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

    public HouseRecordSearch() {
        setRestrictionLogicOperator("and");

        setOrderColumn(SortCol.HouseRecord_CREATE.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }

    @In
    private HouseRecordCondition houseRecordCondition;

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }

    private void resetEjbql(String sql){
        if (!getEjbql().equals(sql)){
            setEjbql(sql);
        }
    }


    public List<HouseRecord> getSearchResult(){
        resetEjbql(houseRecordCondition.getEjbql());
        setRestrictionGroup(getRestrictionGroup());
        return getResultList();
    }

    public Long getResultCount(){
        resetEjbql(houseRecordCondition.getEjbql());
        setRestrictionGroup(getRestrictionGroup());
        return super.getResultCount();
    }


}

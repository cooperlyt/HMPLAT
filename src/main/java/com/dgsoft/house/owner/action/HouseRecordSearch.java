package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.house.owner.model.HouseRecord;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by cooper on 10/3/15.
 */
@Name("houseRecordSearch")
public class HouseRecordSearch extends MultiOperatorEntityQuery<HouseRecord> {

    public enum SortCol{
        OwnerBusiness_recordTime_sort("ownerBusiness.recordTime"),
        Record_location_sort("rs.frame,rs.cabinet,rs.box"),
        House_sort("hr.houseCode"),
        MainHouseOwner_sort("owner.credentialsType,owner.credentialsNumber");


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
            if (getOrderExpress().equals(col.getColPath())){
                return col;
            }
        }
        return null;
    }

    public HouseRecordSearch() {
        setRestrictionLogicOperator("and");

        setOrderColumn(null);
        setEjbql(HouseRecordCondition.SHORT_EJBQL);
        setOrderExpress(SortCol.OwnerBusiness_recordTime_sort.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }


    public void resetPage(){
        setFirstResult(0);
    }


    @In(create = true)
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

        setRestrictionGroup(houseRecordCondition.getRestrictionGroup());
        return getResultList();
    }

    public Long getResultCount(){
        resetEjbql(houseRecordCondition.getEjbql());
        setRestrictionGroup(houseRecordCondition.getRestrictionGroup());
        return super.getResultCount();
    }


}

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

    private static final String EJBQL = "select distinct hr from HouseRecord hr " +
            "left join fetch hr.businessHouse house " +
            "left join fetch house.businessHouseOwner owner " +
            "left join owner.makeCard ownerCard " +
            "left join fetch house.houseBusinessForAfter houseBusiness " +
            "left join fetch houseBusiness.ownerBusiness ownerBusiness " +
            "left join fetch houseBusiness.recordStore rs " +
            "left join house.businessPools pool " +
            "left join pool.makeCard poolCard where 1=2 ";



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
        setRestrictionLogicOperator("or");

        setOrderColumn(null);
        setEjbql(EJBQL);
        setOrderExpress(SortCol.OwnerBusiness_recordTime_sort.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }


    public void resetPage(){
        if ((getFirstResult() == null) || getFirstResult().intValue() ==0)
            setFirstResult(0);
    }


    @In(create = true)
    private HouseRecordCondition houseRecordCondition;

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }


    public List<HouseRecord> getSearchResult(){
        setRestrictionGroup(houseRecordCondition.getRestrictionGroup());
        return getResultList();
    }

    public Long getResultCount(){
        setRestrictionGroup(houseRecordCondition.getRestrictionGroup());
        return super.getResultCount();
    }


}

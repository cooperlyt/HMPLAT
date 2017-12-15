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

    private static final String EJBQL = "select hr from HouseRecord hr left join fetch hr.businessHouse";



    public enum SortCol{
        OwnerBusiness_recordTime_sort("hr.lastChangeTime"),
        House_sort("hr.houseCode");


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
        setEjbql(EJBQL);
        setOrderExpress(SortCol.OwnerBusiness_recordTime_sort.colPath);
        setOrderDirection("desc");
        setMaxResults(20);
    }


    public void resetPage(){
        if ((getFirstResult() == null) || getFirstResult().intValue() ==0)
            setFirstResult(0);
    }



    @Override
    protected void createResultList(){
        setEjbql(houseRecordCondition.getEjbql());
        setRestrictionGroup(houseRecordCondition.getRestrictionGroup());
    }


    public void searchAction(){
        setFirstResult(0);
    }

    @In(create = true)
    private HouseRecordCondition houseRecordCondition;

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }


    public List<HouseRecord> getSearchResult(){

        return getResultList();
    }

    public Long getResultCount(){
        return super.getResultCount();
    }


}

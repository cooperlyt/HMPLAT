package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.RecordStore;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cooper on 4/16/16.
 */
@Name("recordStoreList")
public class RecordStoreList extends MultiOperatorEntityQuery<RecordStore> {



    private static final String EJBQL = "select rs from RecordStore rs " +
            "left join fetch rs.ownerBusiness ob " +
            " where rs.inRoom = false";

    private static final String[] RESTRICTIONS = {
            "rs.id in (#{recordStoreList.searchStoreId})",

    };

    public RecordStoreList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("and");

        setRestrictionGroup(new RestrictionGroup("and",Arrays.asList(RESTRICTIONS)));
        setOrderExpress("rs.ownerBusiness.recordTime,rs.createTime,rs.id");

        setMaxResults(50);

    }

    public List<String> getSearchStoreId(){
        if (searchKey == null || searchKey.trim().equals("")){
            return null;
        }
        String[] result = searchKey.split(",|;");
        if (result.length > 1){
            return Arrays.asList(result);
        }else{
            List<String> resultList = new ArrayList<String>(1);
            resultList.add(searchKey);
            return resultList;
        }
    }

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }



    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }

}

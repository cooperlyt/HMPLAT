package com.dgsoft.house.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.AttachCorporation;
import org.jboss.seam.annotations.Name;

import java.util.*;

/**
 * Created by cooper on 8/26/15.
 */
@Name("attachCorporationList")
public class AttachCorporationList extends MultiOperatorEntityQuery<AttachCorporation> {

    private static final String EJBQL = "select aCorp from AttachCorporation aCorp " +
            "left join fetch aCorp.developer developer " +
            "left join fetch aCorp.mappingCorporation mapping " +
            "left join fetch aCorp.evaluateCorporation evaluate " +
            "left join fetch aCorp.houseSellCompany houseSellCompany";

    private static final String[] RESTRICTIONS1 = {
            "aCorp.type = #{attachCorporationList.type}"
    };

    private static final String[] RESTRICTIONS = {
            "lower(aCorp.id) = lower(#{attachCorporationList.searchKey})",
            "lower(developer.name) like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
            "lower(developer.id) = lower(#{attachCorporationList.searchKey})",
            "lower(mapping.name) like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
            "lower(mapping.id) = lower(#{attachCorporationList.searchKey})",
            "lower(evaluate.name) like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
            "lower(evaluate.id) = lower(#{attachCorporationList.searchKey})",
            "lower(houseSellCompany.id) = lower(#{attachCorporationList.searchKey})",
            "lower(houseSellCompany.name) = lower(#{attachCorporationList.searchKey})"
    };

    private String searchKey;

    private AttachCorpType type;

    public AttachCorpType getType() {
        if (type == null)
            return AttachCorpType.DEVELOPER;
        return type;
    }

    public void setType(AttachCorpType type) {
        this.type = type;
    }

    public void setTypeName(String name){
        if (name == null || name.trim().equals("")){
            type = null;
        }else{
            type = AttachCorpType.valueOf(name);
        }
    }

    public String getTypeName(){
        if (type == null)
            return AttachCorpType.DEVELOPER.name();
        return type.name();
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public AttachCorporationList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("and");
        RestrictionGroup rg = new RestrictionGroup("and", Arrays.asList(RESTRICTIONS1));
        rg.getChildren().add(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
        setRestrictionGroup(rg);
        setOrderColumn("aCorp.recordDate");
        setOrderDirection("desc");
        setMaxResults(25);
    }

    private Map<String,Long> resultCount;

    @Override
    public void refresh(){
        super.refresh();
        resultCount = null;
    }

    public Map<String, Long> getResultTypeCount() {
        if (resultCount == null){
            resultCount = new HashMap<String, Long>();
            AttachCorpType beforType = getType();
            for(AttachCorpType type : AttachCorpType.values()){
                setType(type);
                resultCount.put(type.name(),getResultCount());
            }
            setType(beforType);
        }
        return resultCount;
    }



    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }

}

package com.dgsoft.house.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.Arrays;

/**
 * Created by wxy on 2019-03-07.
 */
@Name("personalContractLogOnList")
@Scope(ScopeType.CONVERSATION)
public class PersonalContractLogOnList extends MultiOperatorEntityQuery<HouseBusiness> {


    private static String EJBQL = "select hb from HouseBusiness hb " +
            "left join hb.afterBusinessHouse h " +
            "left join h.mainOwner owner " +
            "left join hb.ownerBusiness.cards cards " +
            // "left join hb.ownerBusiness.cards cards " +
            "where hb.ownerBusiness.defineId in ('GRSQBA') and cards.type in ('OWNER_RSHIP') ";



    private static final String[] RESTRICTIONS = {
            "(lower(owner.personName) = lower(#{personalContractLogOnList.searchKey})",
            "lower(owner.credentialsNumber) = lower(#{personalContractLogOnList.searchKey})",
            "lower(hb.ownerBusiness.id) = lower(#{personalContractLogOnList.searchKey})",
            "lower(cards.number) = lower(#{personalContractLogOnList.searchKey}))"
    };

    public String getDefine() {
        return define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    private  String define = "GRSQBA";




    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }


    public PersonalContractLogOnList() {
        setEjbql(EJBQL);
        setRestrictionLogicOperator("and"); //连接 where 和
        RestrictionGroup restrictionGroup = new RestrictionGroup("or", Arrays.asList(RESTRICTIONS));
        setRestrictionGroup(restrictionGroup);
        setOrderColumn("hb.ownerBusiness.id");
        setMaxResults(20);
    }

    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }




}

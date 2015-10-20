package com.dgsoft.house.owner.action;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by cooper on 7/6/15.
 */
@Name("houseBusinessList")
public class HouseBusinessList extends HouseBusinessSearch {

    private static final String[] RESTRICTIONS_BIZ = {
            "biz.defineId in (#{houseBusinessList.filterBizDefineId})"
    };



    public HouseBusinessList(){
        super();
        setEjbql(houseBusinessCondition.SHORT_EJBQL);
    }

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    private BusinessCategory businessCategory;

    private String businessDefineId;

    public BusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(BusinessCategory businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessCategoryId(){
        if (businessCategory == null){
            return null;
        }
        return businessCategory.getId();
    }

    public void setBusinessCategoryId(String id){
        if (id == null || id.trim().equals("")){
            businessCategory = null;
        }else{
            businessCategory = systemEntityLoader.getEntityManager().find(BusinessCategory.class,id);
        }

    }

    public String getBusinessDefineId() {
        return businessDefineId;
    }

    public void setBusinessDefineId(String businessDefineId) {
        this.businessDefineId = businessDefineId;
    }

    public List<String> getFilterBizDefineId(){
        if (businessDefineId == null || businessDefineId.trim().equals("")){
            if (businessCategory == null){
                return null;
            }
            List<BusinessDefine> defines = new ArrayList<BusinessDefine>(businessCategory.getBusinessDefines());
            List<String> result = new ArrayList<String>(defines.size());
            for(BusinessDefine define: defines){
                result.add(define.getId());
            }
            return result;
        }else {
            List<String> result = new ArrayList<String>(1);
            result.add(businessDefineId);
            return  result;
        }
    }


    @In(create = true)
    private HouseBusinessCondition houseBusinessCondition;



    @Override
    protected RestrictionGroup getUseRestrictionGroup() {

        return houseBusinessCondition.getRestrictionGroup(RESTRICTIONS_BIZ);
    }

    @Override
    protected String getUseEjbql() {
        return houseBusinessCondition.getEjbql();
    }

    @Override
    public void resetCondition(){
        super.resetCondition();
        houseBusinessCondition.resetCondition();
        businessDefineId = null;
        businessCategory = null;
    }

}

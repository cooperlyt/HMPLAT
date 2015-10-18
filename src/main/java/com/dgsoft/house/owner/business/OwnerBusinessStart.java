package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataFill;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 8/8/15.
 */
@Name("ownerBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class OwnerBusinessStart {

    private static final String BUSINESS_INFO_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";
    private static final String BUSINESS_FILE_PAGE = "/business/houseOwner/BizStartFileUpload.xhtml";
    private static final String BUSINESS_CONFIRM_PAGE = "/business/houseOwner/BizStartConfirm.xhtml";

    @In
    private BusinessDefineHome businessDefineHome;

    public String beginEdit(){
        if (businessDefineHome.isHaveEditSubscribe()){
            businessDefineHome.firstEditGroup();
            return BUSINESS_INFO_PAGE;
        } else{
            return getInfoCompletePath();
        }
    }

    private String getInfoCompletePath(){

        if (RunParam.instance().getBooleanParamValue("CreateUploadFile") || businessDefineHome.isHaveNeedFile()){
            return  BUSINESS_FILE_PAGE;
        }else {
            return BUSINESS_CONFIRM_PAGE;
        }
    }

    public String infoComplete(){

        if (businessDefineHome.isHaveNextEditGroup()){
            businessDefineHome.nextEditGroup();
            return BUSINESS_INFO_PAGE;
        }else{
            if (businessDefineHome.saveEditSubscribes()){
                return getInfoCompletePath();
            }else{
                return BUSINESS_INFO_PAGE;
            }

        }

    }

    @In(required = false)
    private OwnerBusinessFile ownerBusinessFile;

    public boolean isNeedFilePass(){
        if (businessDefineHome.isHaveNeedFile()){
            return (ownerBusinessFile != null) && ownerBusinessFile.isPass();
        }
        return true;
    }



    public String dataSelected(){
        for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
            component.fillData();
        }
        return beginEdit();

    }
}

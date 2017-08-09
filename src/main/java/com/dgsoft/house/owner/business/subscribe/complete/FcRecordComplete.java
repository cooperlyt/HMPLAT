package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-07-25.
 * 凤城归档HOUSE_RECORD SearchKey Display
 */
@Name("fcRecordComplete")
public class FcRecordComplete extends HouseRecordCompleteBase {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    protected String getSearchKey(HouseBusiness houseBusiness) {
        KeyGeneratorHelper key = OwnerHouseHelper.FcgenHouseSearchKeyRecord(houseBusiness.getAfterBusinessHouse());
        if(ownerBusinessHome.getCardNoByType("OWNER_RSHIP")!=null) {
            key.addWord(ownerBusinessHome.getCardNoByType("OWNER_RSHIP").getNumber());
        }

        if(ownerBusinessHome.getCardNoByType("MORTGAGE")!=null) {
            key.addWord(ownerBusinessHome.getCardNoByType("MORTGAGE").getNumber());
        }

        if(ownerBusinessHome.getCardNoByType("NOTICE")!=null) {
            key.addWord(ownerBusinessHome.getCardNoByType("NOTICE").getNumber());
        }

        if(ownerBusinessHome.getCardNoByType("NOTICE_MORTGAGE")!=null) {
            key.addWord(ownerBusinessHome.getCardNoByType("NOTICE_MORTGAGE").getNumber());
        }

        if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial()!=null){
            key.addWord(ownerBusinessHome.getInstance().getMortgaegeRegiste().getFinancial().getName());
        }

        if(ownerBusinessHome.getInstance().getMortgaegeRegiste()!=null && ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial()!=null){
            key.addWord(ownerBusinessHome.getInstance().getMortgaegeRegiste().getOldFinancial().getName());
        }
        if(ownerBusinessHome.getInstance().getDefineId().equals("WP18") || ownerBusinessHome.getInstance().getDefineId().equals("WP19") || ownerBusinessHome.getInstance().getDefineId().equals("WP19")){
            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getPersonName()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getPersonName());
            }
            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getCredentialsNumber()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getCredentialsNumber());
            }
        }
        if(ownerBusinessHome.getInstance().getDefineId().equals("WP73") || ownerBusinessHome.getInstance().getDefineId().equals("WP74")){

            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getPersonName()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getPersonName());
            }

            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getCredentialsNumber()!=null){
                key.addWord(ownerBusinessHome.getApplyPersion().getCredentialsNumber());
            }
            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getHouseCardNo()!=null && !ownerBusinessHome.getCloseHouse().getHouseCardNo().equals("")){
                key.addWord(ownerBusinessHome.getCloseHouse().getHouseCardNo());
            }
            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getHouseCardNo()!=null && !ownerBusinessHome.getCloseHouseCancel().getHouseCardNo().equals("")){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getHouseCardNo());
            }

            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getLegalDocuments()!=null){
                key.addWord(ownerBusinessHome.getCloseHouse().getLegalDocuments());
            }
            if (ownerBusinessHome.getCloseHouse()!=null && ownerBusinessHome.getCloseHouse().getExecutionNotice()!=null){
                key.addWord(ownerBusinessHome.getCloseHouse().getExecutionNotice());
            }

            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getLegalDocuments()!=null){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getLegalDocuments());
            }
            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getExecutionNotice()!=null){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getExecutionNotice());
            }
            if (ownerBusinessHome.getCloseHouseCancel()!=null && ownerBusinessHome.getCloseHouseCancel().getHouseCardNo()!=null){
                key.addWord(ownerBusinessHome.getCloseHouseCancel().getHouseCardNo());
            }



        }

        return key.getKey();
    }

    @Override
    protected String getDisplay(HouseBusiness houseBusiness) {
        return OwnerHouseHelper.FcgenHouseDisplay(houseBusiness.getAfterBusinessHouse(),ownerBusinessHome);
    }

}

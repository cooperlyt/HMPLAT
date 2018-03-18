package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.SubStatus;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Logging;

import java.util.List;

/**
 * Created by cooper on 11/1/15.
 */
@Name("businessCancel")
public class BusinessCancel {

    @In
    private AuthenticationInfo authInfo;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    //RUNNING, COMPLETE, ABORT, SUSPEND, CANCEL, MODIFYING, COMPLETE_CANCEL;
    protected void resetHouseRecord(){
        for(HouseBusiness houseBusiness: ownerBusinessHome.getInstance().getHouseBusinesses()){
            HouseRecord hr =ownerBusinessHome.getEntityManager().find(HouseRecord.class,houseBusiness.getHouseCode());
            List<HouseBusiness> houseBusinessList = ownerBusinessHome.getEntityManager().createQuery("select houseBusiness from HouseBusiness houseBusiness " +
                    "where houseBusiness.houseCode = :houseCode and houseBusiness.id <> :houseBusinessId and " +
                    "houseBusiness.ownerBusiness.status in ('COMPLETE','MODIFYING','COMPLETE_CANCEL') order by houseBusiness.ownerBusiness.regTime desc ", HouseBusiness.class)
                    .setParameter("houseCode", houseBusiness.getHouseCode())
                    .setParameter("houseBusinessId", houseBusiness.getId())
                    .getResultList();


            if (houseBusinessList.isEmpty()){
                if (hr != null){

                    houseBusiness.getAfterBusinessHouse().getHouseRecords().clear();
                    ownerBusinessHome.getEntityManager().remove(hr);
                }
            }else{
                if (hr != null){
                    hr.setBusinessHouse(houseBusinessList.get(0).getAfterBusinessHouse());
                    hr.setHouseStatus(OwnerHouseHelper.instance().getMasterStatus(houseBusiness.getHouseCode()));
                }else{
                    ownerBusinessHome.getEntityManager().persist(new HouseRecord(houseBusinessList.get(0).getAfterBusinessHouse(),OwnerHouseHelper.instance().getMasterStatus(houseBusiness.getHouseCode())));
                }
            }
        }
    }

    public String cancelBusiness() {
        if (!ownerBusinessHome.isCanCancel()) {
            return null;
        }
        //TODO add taskOper
        //if (BusinessInstance.BusinessSource.BIZ_AFTER_SAVE.equals(ownerBusinessHome.getInstance().getSource())){

            ownerBusinessHome.getInstance().setStatus(BusinessInstance.BusinessStatus.ABORT);
            for(SubStatus subStatus: ownerBusinessHome.getInstance().getSubStatuses()){
                subStatus.setStatus(BusinessInstance.BusinessStatus.ABORT);
            }
            ownerBusinessHome.getInstance().setRecorded(false);
            if(ownerBusinessHome.getInstance().getSelectBusiness() != null){
                ownerBusinessHome.getInstance().getSelectBusiness().setStatus(BusinessInstance.BusinessStatus.COMPLETE);
            }
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(TaskOper.OperType.TERMINATION, ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), comments));
            resetHouseRecord();
            if ("updated".equals(ownerBusinessHome.update())){
                Events.instance().raiseEvent("com.coopersoft.businessCancel." + ownerBusinessHome.getInstance().getDefineId(),comments);
                return "Business_is_Deleted";
            }else
            {
                return null;
            }


        //}

       // return "Cancel_Business_Create";
    }

}

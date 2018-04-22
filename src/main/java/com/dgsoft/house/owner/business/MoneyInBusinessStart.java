package com.dgsoft.house.owner.business;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MoneyBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

/**
 * Created by wxy on 2018-04-21.
 */

@Name("moneyInBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class MoneyInBusinessStart {

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private OwnerBusinessStart ownerBusinessStart;

    @In(create = true)
    private FacesMessages facesMessages;

    private String selectBizId;

    public String getSelectBizId() {
        return selectBizId;
    }

    public void setSelectBizId(String selectBizId) {
        this.selectBizId = selectBizId;
    }

    public String businessSelected() {
        ownerBusinessHome.getInstance().setSelectBusiness(ownerBusinessHome.getEntityManager().find(OwnerBusiness.class, selectBizId));
        ownerBusinessHome.getInstance().getMoneyBusinesses().clear();
        for  (MoneyBusiness mb:ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusinesses()){
            ownerBusinessHome.getInstance().getMoneyBusinesses().add(new MoneyBusiness(ownerBusinessHome.getInstance(),mb));
        }
        for (MoneyBusiness omb:ownerBusinessHome.getInstance().getSelectBusiness().getMoneyBusinesses()){
            omb.setStatus(MoneyBusiness.MoneyBusinessStatus.CHANGED);

        }
        if (ownerBusinessHome.getInstance().getMoneyBusinesses().isEmpty()){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"selectMoneyInBusinessIsEmpty");
            return null;
        }else {
            return ownerBusinessStart.dataSelected();
        }
    }





}

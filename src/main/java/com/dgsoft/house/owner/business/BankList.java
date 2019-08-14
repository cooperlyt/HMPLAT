package com.dgsoft.house.owner.business;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Bank;
import com.dgsoft.house.owner.model.BusinessBuild;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.List;

/**
 * Created by wxy on 2019-08-14.
 */
@Name("bankList")
@Scope(ScopeType.CONVERSATION)
public class BankList {

    @In(create = true)
    protected OwnerEntityLoader ownerEntityLoader;

    private String bankId;

    private String buildCode;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }


    public BusinessBuild SearchBusinessBuild(){
        BusinessBuild bizBuild = null;
        List<BusinessBuild> businessBuilds = ownerEntityLoader.getEntityManager().createQuery("select bizBuid from BusinessBuild bizBuid where bizBuid.businessProject.ownerBusiness.status in('COMPLETE') and bizBuid.businessProject.ownerBusiness.type<>'CANCEL_BIZ' and bizBuid.buildCode=:buildCode", BusinessBuild.class)
                .setParameter("buildCode", getBuildCode()).getResultList();
        if (businessBuilds !=null && businessBuilds.size()>0){
            return businessBuilds.get(0);
        }
        return bizBuild;
    }


    public Bank SearchBank(){
        return ownerEntityLoader.getEntityManager().find(Bank.class,getBankId());
    }





}

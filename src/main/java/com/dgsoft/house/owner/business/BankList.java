package com.dgsoft.house.owner.business;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Bank;
import com.dgsoft.house.owner.model.BusinessBuild;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

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

    public BusinessBuild getBusinessBuild() {
        return businessBuild;
    }

    public void setBusinessBuild(BusinessBuild businessBuild) {
        this.businessBuild = businessBuild;
    }

    private BusinessBuild businessBuild = null;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    private Bank bank = null;




    public void SearchBusinessBuild(String buildCode){
        if (businessBuild == null) {
            List<BusinessBuild> businessBuilds = ownerEntityLoader.getEntityManager().createQuery("select bizBuid from BusinessBuild bizBuid where bizBuid.businessProject.ownerBusiness.status in('COMPLETE') and bizBuid.businessProject.ownerBusiness.type<>'CANCEL_BIZ' and bizBuid.buildCode=:buildCode", BusinessBuild.class)
                    .setParameter("buildCode", buildCode).getResultList();
            if (businessBuilds != null && businessBuilds.size() > 0) {
                businessBuild = businessBuilds.get(0);

            }
            if (businessBuild!= null && businessBuild.getBusinessProject()!=null && businessBuild.getBusinessProject().getMoneySafe()!=null){
                if (bank == null){
                    bank = ownerEntityLoader.getEntityManager().find(Bank.class,businessBuild.getBusinessProject().getMoneySafe().getBank());
                }
            }
        }

    }

    public List<Bank> getBankList(){
        return ownerEntityLoader.getEntityManager().createQuery("select bank from Bank bank",Bank.class).getResultList();
    }






}

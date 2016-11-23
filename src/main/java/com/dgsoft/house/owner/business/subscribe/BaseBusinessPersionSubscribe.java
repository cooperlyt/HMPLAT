package com.dgsoft.house.owner.business.subscribe;

import cc.coopersoft.house.ProxyType;
import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessPersion;
import com.dgsoft.house.owner.model.ProxyPerson;
import org.jboss.seam.annotations.In;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-19
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */

public abstract class BaseBusinessPersionSubscribe extends OwnerEntityHome<BusinessPersion> {

    @In
    protected OwnerBusinessHome ownerBusinessHome;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    private boolean have;

    protected abstract BusinessPersion.PersionType getType();


    @Override
    public Class<BusinessPersion> getEntityClass() {
        return BusinessPersion.class;
    }

    @Override
    public void create() {
        super.create();
        String findId = searchByDB();
        if (findId != null) {
            have = true;
            setId(findId);
        } else {
            have = false;
        }


    }

    private String searchByDB() {
        for (BusinessPersion businessPersion : ownerBusinessHome.getInstance().getBusinessPersions()) {
            if (businessPersion.getType().equals(getType())) {
                return businessPersion.getId();
            }
        }
        return null;
    }

    public void checkHave() {

        if (have) {
            String findId = searchByDB();
            if (findId != null) {
                setId(findId);
            }
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getBusinessPersions().add(getInstance());
        } else {
            for (BusinessPersion businessPersion : ownerBusinessHome.getInstance().getBusinessPersions()) {
                if (businessPersion.getType().equals(getType())) {
                    ownerBusinessHome.getInstance().getBusinessPersions().remove(businessPersion);
                    break;
                }
            }
            clearInstance();
        }
    }

    @Override
    protected BusinessPersion createInstance() {
        return new BusinessPersion(getType());
    }

    private PersonHelper<BusinessPersion> personHelper;

    public PersonHelper<BusinessPersion> getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new PersonHelper<BusinessPersion>(getInstance());
        }
        return personHelper;
    }

    private PersonHelper<ProxyPerson> proxyPersonPersonHelper;

    public PersonHelper<ProxyPerson> getProxyPersonHelper() {
        return proxyPersonPersonHelper;
    }

    private ProxyType getProxyType(){
        if (proxyPersonPersonHelper == null){
            return null;
        }else{
            return proxyPersonPersonHelper.getPersonEntity().getProxyType();
        }

    }

    private void setProxyType(ProxyType proxyType){
        if (proxyType == null){
            proxyPersonPersonHelper = null;
            getInstance().setProxyPerson(null);
        }else{
            if(proxyPersonPersonHelper == null){
                proxyPersonPersonHelper = new PersonHelper<ProxyPerson>(new ProxyPerson());
            }
            proxyPersonPersonHelper.getPersonEntity().setProxyType(proxyType);
            getInstance().setProxyPerson(proxyPersonPersonHelper.getPersonEntity());
        }

    }

}

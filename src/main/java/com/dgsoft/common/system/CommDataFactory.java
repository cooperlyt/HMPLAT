package com.dgsoft.common.system;

import cc.coopersoft.house.ProxyType;
import cc.coopersoft.house.UseType;
import cc.coopersoft.house.sale.data.PowerPerson;
import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.HouseProperty;
import com.dgsoft.house.OwnerShareCalcType;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cooper on 9/25/16.
 */
@Name("commDataFactory")
public class CommDataFactory {

    @Factory(value = "credentialsTypes",scope = ScopeType.APPLICATION )
    public PersonEntity.CredentialsType[] getCredentialsTypes(){
        return PersonEntity.CredentialsType.values();
    }

    @Factory(value = "personCredentialsTypes", scope = ScopeType.APPLICATION)
    public List<PersonEntity.CredentialsType> getPersonCredentialsTypes(){
        List<PersonEntity.CredentialsType> result = new ArrayList<PersonEntity.CredentialsType>();
        for(PersonEntity.CredentialsType ct: getCredentialsTypes()){
            if (!ct.isCorp()){
                result.add(ct);
            }
        }
        return result;
    }

    @Factory(value = "proxyTypes", scope = ScopeType.APPLICATION)
    public ProxyType[] getProxyTypes(){
        return ProxyType.values();
    }

    @Factory(value = "ownerShareCalcTypes",scope = ScopeType.APPLICATION)
    public OwnerShareCalcType[] getOwnerShareCalcTypes(){return OwnerShareCalcType.values();}

    @Factory(value = "legalTypes",scope = ScopeType.CONVERSATION)
    public PowerPerson.LegalType[] getLegalTypes() {
        return PowerPerson.LegalType.values();
    }

    @Factory(value = "sexValues",scope = ScopeType.APPLICATION)
    public Sex[] getSexValues(){
        return Sex.values();
    }

    @Factory(value = "useTypes", scope = ScopeType.APPLICATION)
    public UseType[] getUseTypes(){
        return UseType.values();
    }

    @Factory(value = "houseProperties", scope = ScopeType.APPLICATION)
    public HouseProperty[] getHouseProperty(){
        return HouseProperty.values();
    }

    @Factory(value = "corpTypes", scope = ScopeType.APPLICATION)
    public AttachCorpType[] getAttachCorpTypes(){
        return AttachCorpType.values();
    }

}

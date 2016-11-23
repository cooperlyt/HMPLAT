package com.dgsoft.common.system;

import cc.coopersoft.house.ProxyType;
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


}

package com.dgsoft.house.owner.ws;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by cooper on 9/4/15.
 */

@Name("developerService")
@Scope(ScopeType.STATELESS)
@WebService(name = "DeveloperService")
@HandlerChain(file = "soap-handlers.xml")
public class DeveloperService{

    @WebMethod
    public String logon(String userId, String password, String random) {
        return DeveloperServiceComponent.instance().logon(userId, password, random);
    }

    @WebMethod
    public String getBuildGridMap(String buildCode){
        return DeveloperServiceComponent.instance().getBuildGridMap(buildCode);
    }

    @WebMethod
    public String getHouseInfoBySale(String userId, String houseCode){
        return DeveloperServiceComponent.instance().getHouseInfoBySale(userId,houseCode);
    }

    @WebMethod
    public String applyContractNumber(String userId, int count , String typeName){
        return DeveloperServiceComponent.instance().applyContractNumber(userId,count,typeName);
    }

    @WebMethod
    public String submitContract(String contract, String userId) {
        return OutsideBusinessCreate.instance().submitContract(contract,userId);
    }

}

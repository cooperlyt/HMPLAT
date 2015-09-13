package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.developersale.LogonStatus;
import com.dgsoft.developersale.wsinterface.DESUtil;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.action.BuildHome;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.ProjectCard;
import com.longmai.uitl.Base64;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

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
        return DeveloperServiceComponent.instance().logon(userId,password,random);
    }

    @WebMethod
    public String getBuildGridMap(String buildCode){
        return DeveloperServiceComponent.instance().getBuildGridMap(buildCode);
    }

    @WebMethod
    public String submitContract(String contract, String userId) {
        return null;
    }

//    public String searchContract(String houseCode){
//
//
//    }


}

package com.dgsoft.common.system.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by cooper on 10/17/15.
 */
@WebService()
public class ExtendsMessageService {



    @WebMethod
    public boolean personCerInfo(String key, String cer){

       return ExtendsServiceComponent.instance().personCerRead(key,cer);

    }
}

package com.dgsoft.common.system.ws;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by cooper on 10/17/15.
 */
@Name("extendsMessageService")
@Scope(ScopeType.STATELESS)
@WebService(name = "ExtendsMessageService")
@HandlerChain(file = "soap-handlers.xml")
public class ExtendsMessageService {



    @WebMethod()
    public boolean personCerInfo(@WebParam(name = "key" ,targetNamespace = "http://ws.system.common.dgsoft.com/")String key, @WebParam(name = "cer", targetNamespace = "http://ws.system.common.dgsoft.com/")String cer){
        Logging.getLog("recive data:" + cer);
       return ExtendsServiceComponent.instance().personCerRead(key,cer);

    }
}

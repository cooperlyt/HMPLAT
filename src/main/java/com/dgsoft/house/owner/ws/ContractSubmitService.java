package com.dgsoft.house.owner.ws;

import org.jboss.seam.log.Logging;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by cooper on 18/11/2016.
 */
@Path("/contractSubmit")
public class ContractSubmitService {


    @POST
    @Path("/saleContract/{userId}")
    public String commitContract(@PathParam("userId") String userId,@FormParam("contract") String contract){
        Logging.getLog(getClass()).debug(userId + " commit Contract:" + contract);
        return OutsideBusinessCreate.instance().submitContract(contract,userId);
    }

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String status(){
        return "service is running";
    }

}

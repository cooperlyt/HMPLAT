package com.dgsoft.house.owner.ws;

import cc.coopersoft.house.SubmitType;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.seam.log.Logging;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;

/**
 * Created by cooper on 9/26/16.
 */
@Path("/extends")
public class ExtendsCall {


    @POST
    @Path("/contract/{type}/{key}")
    public String submitBusiness(@PathParam("type") String type,
                                    @PathParam("key") String attrEmpId,
                                    @FormParam("data") String data){
        Logging.getLog(getClass()).info(attrEmpId + " submit " + type );
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(OutsideBusinessCreate.instance().submitBusiness(SubmitType.valueOf(type),data,attrEmpId));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }

    @POST
    @Path("/contract_and_house/{key}")
    public String submitHouseContract(
                                 @PathParam("key") String attrEmpId,
                                 @FormParam("source") String source, @FormParam("data") String contract){
        Logging.getLog(getClass()).info(attrEmpId  );
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(OutsideBusinessCreate.instance().submitHouseAndContract(source,contract,attrEmpId));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }

    @POST
    @Path("/person/{key}")
    public String putPerson(@PathParam("key") String key , @FormParam("cer") String cer){

        Logging.getLog(this.getClass()).debug("receive person card data:" + cer);
        JSONObject contractJson;
        try {
            if (cer != null && !"".equals(cer.trim())){
                contractJson = new JSONObject(cer);
            }else{
                contractJson = new JSONObject();
            }
            contractJson.put("pushType","person");

            return pushMessage(key, contractJson.toString());
        } catch (JSONException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }

    }

    private String pushMessage(String key,String cer) {
        TopicKey topicKey = new TopicKey(key);
        TopicsContext topicsContext = TopicsContext.lookup();
        try {
            topicsContext.publish(topicKey, cer);
            // topicsContext.removeTopic(topicKey);

            return "success";

        } catch (MessageException e) {
            topicsContext.removeTopic(topicKey);
            Logging.getLog(getClass()).debug(e.getMessage(),e);
            return "failed";
        }
    }

    @POST
    @Path("/finger/code/{key}")
    public String putFingerCode(@PathParam("key") String key , @FormParam("code") String code){

        Logging.getLog(this.getClass()).debug("receive FingerCode:" + code);
        JSONObject contractJson = new JSONObject();
        try {
            if (code != null && !"".equals(code.trim())){
                contractJson.put("fingerCode",code);

            }
            contractJson.put("pushType","finger");

            return pushMessage(key, contractJson.toString());
        } catch (JSONException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }

    @POST
    @Path("/finger/valid/{key}")
    public String validFingerCode(@PathParam("key") String key , @FormParam("img") String img){

        Logging.getLog(this.getClass()).debug("receive image:" + img);


        return pushMessage(key, img);

    }

}

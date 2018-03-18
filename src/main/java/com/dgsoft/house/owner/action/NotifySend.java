package com.dgsoft.house.owner.action;

import cc.coopersoft.comm.HttpJsonDataGet;
import com.dgsoft.common.system.RunParam;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.log.Logging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by cooper on 10/03/2018.
 */
@Name("notifySend")
public class NotifySend {

    public enum MessageType{
        COMPLETE,ABORT,OTHER
    }

    public static class Messages{

        private String houseId;
        private MessageType type;
        private Date sendTime;
        private String bizType;
        private String bizId;
        private String description;

        public Messages() {
        }

        public Messages(String houseId, MessageType type, String bizType, String bizId, String description) {
            this.houseId = houseId;
            this.type = type;
            this.bizType = bizType;
            this.bizId = bizId;
            this.description = description;
            this.sendTime = new Date();
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        @JsonProperty("send_time")
        public Date getSendTime() {
            return sendTime;
        }

        public void setSendTime(Date sendTime) {
            this.sendTime = sendTime;
        }

        public MessageType getType() {
            return type;
        }

        public void setType(MessageType type) {
            this.type = type;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getBizId() {
            return bizId;
        }

        public void setBizId(String bizId) {
            this.bizId = bizId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // @Observer("cc.copersoft.houseStatusChangeEvent")
    @Asynchronous
    public void onMessageSend(List<Messages> messages){
        HttpPost httpPost = new HttpPost(RunParam.instance().getStringParamValue("MessageServerAddress"));
        httpPost.setHeader("Accept-Charset", "UTF-8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            values.add(new BasicNameValuePair("data",mapper.writeValueAsString(messages) ));
            httpPost.setEntity(new UrlEncodedFormEntity(values, "UTF-8"));

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (204 == responseCode){
                Logging.getLog(this.getClass()).debug("房屋改变消息已发送失败！");
            }else{
                Logging.getLog(this.getClass()).error("房屋改变消息发送失败！服务器返回：" + responseCode);
            }
        } catch (JsonProcessingException e) {
            Logging.getLog(this.getClass()).error("房屋改变消息发送失败！",e);
        } catch (UnsupportedEncodingException e) {
            Logging.getLog(this.getClass()).error("房屋改变消息发送失败！",e);
        } catch (ClientProtocolException e) {
            Logging.getLog(this.getClass()).error("房屋改变消息发送失败！",e);
        } catch (IOException e) {
            Logging.getLog(this.getClass()).error("房屋改变消息发送失败！",e);
        }

    }

}

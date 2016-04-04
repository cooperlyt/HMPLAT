package com.dgsoft.common;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

import java.util.*;

/**
 * Created by cooper on 3/13/16.
 */
@Name("fileExtendUploadPublish")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class FileExtendUploadPublish {


    public interface FileExtendUploadSubscribe{

        boolean receiveFile(String key,List<String> fileIds);

    }


    private Map<FileExtendUploadSubscribe,String> subscribes = new HashMap<FileExtendUploadSubscribe, String>();


    public String register(FileExtendUploadSubscribe subscribe){
        String result = UUID.randomUUID().toString();
        subscribes.put(subscribe,result);
        return result;
    }


    @Asynchronous
    public void unregister(FileExtendUploadSubscribe subscribe){
        String key = subscribes.get(subscribe);

        TopicKey topicKey = new TopicKey(key);
        TopicsContext topicsContext = TopicsContext.lookup();
        topicsContext.removeTopic(topicKey);

        if (key != null) {
            subscribes.remove(subscribe);
        }

    }

    // dataFormat {'key':['fileId1','fileId2']}
    public void publish(JSONObject jsonObject) throws JSONException {

        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String fileKey = (String) it.next();
            JSONArray fileIdArray = jsonObject.getJSONArray(fileKey);
            List<String> fileIds = new ArrayList<String>(fileIdArray.length());
            for(int i = 0 ; i < fileIdArray.length(); i++){
                fileIds.add(fileIdArray.getString(i));
            }
            for(FileExtendUploadSubscribe subscribe: subscribes.keySet()){
                if (subscribe.receiveFile(fileKey,fileIds)){
                    String pushKey = subscribes.get(subscribe);

                    TopicKey topicKey = new TopicKey(pushKey);
                    TopicsContext topicsContext = TopicsContext.lookup();
                    try {
                        topicsContext.publish(topicKey, jsonObject.toString());


                    } catch (MessageException e) {
                        topicsContext.removeTopic(topicKey);
                        subscribes.remove(subscribe);
                        Logging.getLog(getClass()).warn(e.getMessage(),e);
                    }

                    return;
                }
            }
        }

    }


    public static FileExtendUploadPublish instance()
    {
        return (FileExtendUploadPublish) Component.getInstance(FileExtendUploadPublish.class, ScopeType.APPLICATION,true);
    }

}

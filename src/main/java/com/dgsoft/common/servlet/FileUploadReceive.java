package com.dgsoft.common.servlet;

import org.jboss.seam.log.Logging;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by cooper on 3/12/16.
 */
public class FileUploadReceive extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuffer json = new StringBuffer();
        String line;

        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }


        Logging.getLog(getClass()).debug("receive data:" + json);

        try {
            JSONObject jsonObject = new JSONObject(json.toString());


            //Logging.getLog(getClass()).debug("receive json data:" + jsonObject.toString());

            // Logging.getLog(getClass()).debug("receive json data:" );

            String key = jsonObject.getString("key");
            //Logging.getLog(getClass()).debug("push key:" + key);

            TopicKey topicKey = new TopicKey(key);
            TopicsContext topicsContext = TopicsContext.lookup();
            try {

                topicsContext.publish(topicKey, jsonObject.toString());

            } catch (MessageException e) {
                topicsContext.removeTopic(topicKey);

                Logging.getLog(getClass()).warn(e.getMessage(), e);
            }

        } catch (JSONException e) {
            throw new ServletException(e);
        }
        // FileExtendUploadPublish.instance().publish(new JSONObject(json.toString()));

    }

}

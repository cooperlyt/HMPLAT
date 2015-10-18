package com.dgsoft.common.system.ws;

import com.dgsoft.common.system.PersonIDCard;
import com.dgsoft.common.system.model.Person;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cooper on 10/17/15.
 */
@Name("extendsServiceComponent")
public class ExtendsServiceComponent {

    public boolean personCerRead(String key, String data){
        EntityManager systemEntityManager = (EntityManager) Component.getInstance("systemEntityManager", true, true);



        String number;
        try {
            JSONObject jsonObject = new JSONObject(data);
            number = jsonObject.getString("number");
            if (number == null || number.trim().equals("")){
                return false;
            }
            Person person = systemEntityManager.find(Person.class, number);
            Boolean persist = false;
            if (person == null){
                person = new Person(number);
                persist = true;

            }

            person.setName(jsonObject.getString("name"));


            try{
                person.setCredentialsOrgan(jsonObject.getString("org"));
            } catch (JSONException e) {
                person.setCredentialsOrgan(null);
            }
            try{
                person.setSex(PersonIDCard.Sex.valueOf(jsonObject.getString("sex")));
            } catch (JSONException e) {
                person.setSex(null);
            }
            try{
                person.setEthnic(jsonObject.getString("ethnic"));
            } catch (JSONException e) {
                person.setEthnic(null);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                person.setDateOfBirth(sdf.parse(jsonObject.getString("dateOfBirth")));
            } catch (JSONException e) {
                person.setDateOfBirth(null);
            } catch (ParseException e) {
                person.setDateOfBirth(null);
            }
            try{
                person.setAddress(jsonObject.getString("address"));
            } catch (JSONException e) {
                person.setAddress(null);
            }


            try{
                person.setValidDateBegin(sdf.parse(jsonObject.getString("validDateBegin")));
            } catch (JSONException e) {
                person.setDateOfBirth(null);
            } catch (ParseException e) {
                person.setDateOfBirth(null);
            }

            try{
                person.setValidDateEnd(sdf.parse(jsonObject.getString("validDateEnd")));
            } catch (JSONException e) {
                person.setDateOfBirth(null);
            } catch (ParseException e) {
                person.setDateOfBirth(null);
            }

            person.setReadTime(new Date());

            if (persist){
                systemEntityManager.persist(person);
            }
            systemEntityManager.flush();

            TopicKey topicKey = new TopicKey(key);
            TopicsContext topicsContext = TopicsContext.lookup();

            try {
                topicsContext.publish(topicKey, number);
                topicsContext.removeTopic(topicKey);

                return true;
            } catch (MessageException e) {
                Logging.getLog(getClass()).debug(e.getMessage(),e);
                return false;
            }

        } catch (JSONException e) {
            Logging.getLog(getClass()).debug(e.getMessage(),e);
            return false;
        }



    }

    public static ExtendsServiceComponent instance()
    {
        return (ExtendsServiceComponent) Component.getInstance("extendsServiceComponent", true, true);
    }

}

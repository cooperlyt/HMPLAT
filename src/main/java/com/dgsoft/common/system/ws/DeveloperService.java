package com.dgsoft.common.system.ws;

import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.DeveloperLogonKey;
import com.longmai.uitl.Base64;
import org.jboss.seam.Component;
import org.jboss.seam.log.Logging;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by cooper on 9/4/15.
 */
@WebService
public class DeveloperService {

    @WebMethod
    public String logon(String userId, String password, String random) {

        JSONObject jsonObject = new JSONObject();

        EntityManager entityManager = (EntityManager) Component.getInstance("houseEntityManager", true, true);

        DeveloperLogonKey key = entityManager.find(DeveloperLogonKey.class, userId);
        try {
            if (key == null) {
                jsonObject.put("logonStatus", "KEY_NOT_FOUND");
            }else if(!CheckHashValues(key.getPassword(), random, password)){
                jsonObject.put("logonStatus", "PASSWORD_ERROR");
            }else if (!AttachCorporation.AttachCorpType.DEVELOPER.equals(key.getAttachEmployee().getAttachCorporation().getType())){
                jsonObject.put("logonStatus", "TYPE_ERROR");
            }else {
                jsonObject.put("logonStatus", "LOGON");
                jsonObject.put("employeeName", key.getAttachEmployee().getPersonName());

              //  jsonObject.put("corp",)

            }

        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Logging.getLog(getClass()).error(e2.getMessage(),e2);
            return null;
        }

        return null;
    }

   // private JSONObject get

    private boolean CheckHashValues(String Seed, String Random, String ClientDigest) throws NoSuchAlgorithmException {

        Logging.getLog(getClass()).debug("seed:" + Seed + ";random:" + Random + ";ClientDigest:" + ClientDigest);
        MessageDigest md = MessageDigest.getInstance("SHA1");
        String a = Random + Seed;
        byte[] serverDigest = md.digest(a.getBytes());
        byte[] clientDigest = Base64.decode(ClientDigest);

        return Arrays.equals(serverDigest, clientDigest);

    }

}

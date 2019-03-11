package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.AttachEmployee;
import com.dgsoft.house.model.DeveloperLogonKey;
import com.dgsoft.house.model.PersonalContractLogOn;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import java.util.*;


/**
 * Created by wxy on 2019-03-09.
 */
@Name("personalContractHome")
@Scope(ScopeType.CONVERSATION)
public class PersonalContractHome extends HouseEntityHome<PersonalContractLogOn>{

    private static final String NUMBER_KEY = "ATTACH_EMPLOYEE_ID";


    private String selectKeyId;
    private String businessId;
    private String keyUid;
    private String keySeed;
    private String keyDesKey;
    private List<PersonalContractLogOn> personalContractLogOnList;





    private HouseBusiness houseBusiness;

    public HouseBusiness getHouseBusiness() {
        return houseBusiness;
    }

    public void setHouseBusiness(HouseBusiness houseBusiness) {
        this.houseBusiness = houseBusiness;
    }

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;


    @In
    private FacesMessages facesMessages;


    public String getSelectKeyId() {
        return selectKeyId;
    }

    public void setSelectKeyId(String selectKeyId) {
        this.selectKeyId = selectKeyId;
    }
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }





    public String getKeyDesKey() {
        return keyDesKey;
    }

    public void setKeyDesKey(String keyDesKey) {
        this.keyDesKey = keyDesKey;
    }

    public String getKeySeed() {
        return keySeed;
    }

    public void setKeySeed(String keySeed) {
        this.keySeed = keySeed;
    }

    public String getKeyUid() {
        return keyUid;
    }

    public void setKeyUid(String keyUid) {
        this.keyUid = keyUid;
    }

    @Override
    protected PersonalContractLogOn createInstance(){
        return new PersonalContractLogOn(businessId,true,new Date());
    }


    public List<PersonalContractLogOn> getPersonalContractLogOnList(){
        personalContractLogOnList = houseEntityLoader.getEntityManager().createQuery("select pc from PersonalContractLogOn pc where pc.businessID = :biz", PersonalContractLogOn.class).setParameter("biz",businessId).getResultList();
        return personalContractLogOnList;
    }

    public void serchHB(){

        houseBusiness = ownerEntityLoader.getEntityManager().createQuery("select hb from HouseBusiness hb where hb.ownerBusiness.id=:biz",HouseBusiness.class)
                .setParameter("biz",businessId).getResultList().get(0);
        setHouseBusiness(houseBusiness);

    }


    public String addKey(){
        if (getEntityManager().createQuery("select count(logonKey.id) from PersonalContractLogOn logonKey where logonKey.id = :keyId",Long.class)
                .setParameter("keyId",keyUid).getSingleResult().compareTo(Long.valueOf(0)) <= 0) {
            Logging.getLog(getClass()).debug("businessId------"+businessId);
            serchHB();
            getInstance().setId(keyUid);
            getInstance().setPassword(keySeed);
            getInstance().setUserKey(keyDesKey);
            getInstance().setHouseCode(getHouseBusiness().getHouseCode());

            GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
            gc.setTime(new Date());
            gc.add(Calendar.YEAR, 1);
            getInstance().setDateTo(gc.getTime());
            Logging.getLog(getClass()).debug("bindKey:" + keyUid + "," + keySeed + "," + keyDesKey) ;

            return persist();
        }else{
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"KeyIsUsed");
            return null;
        }

    }


    public void deleteKey(){
        Logging.getLog(getClass()).debug("selectKeyId:" + selectKeyId);
        PersonalContractLogOn pl = houseEntityLoader.getEntityManager().find(PersonalContractLogOn.class,selectKeyId);
        if (pl!=null){
            houseEntityLoader.getEntityManager().remove(pl);
            houseEntityLoader.getEntityManager().flush();
            return;
        }
    }


}

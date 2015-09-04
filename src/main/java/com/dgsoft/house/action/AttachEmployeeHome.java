package com.dgsoft.house.action;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.AttachEmployee;
import com.dgsoft.house.model.DeveloperLogonKey;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import java.util.*;

/**
 * Created by cooper on 9/2/15.
 */
@Name("attachEmployeeHome")
public class AttachEmployeeHome extends HouseEntityHome<AttachEmployee> {

    private static final String NUMBER_KEY = "ATTACH_EMPLOYEE_ID";

    @In(create = true)
    private AttachCorporationHome attachCorporationHome;

    @In
    private FacesMessages facesMessages;

    @Override
    protected boolean verifyPersistAvailable() {
        getInstance().setAttachCorporation(attachCorporationHome.getInstance());
        GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
        gc.setTime(new Date());
        gc.add(Calendar.YEAR, 1);
        getInstance().setLicenseTimeTo(gc.getTime());
        return true;
    }

    @Override
    protected AttachEmployee createInstance(){
        return new AttachEmployee(HouseNumberBuilder.instance().useNumber(NUMBER_KEY,8),true,new Date());
    }

    private PersonHelper<AttachEmployee> personHelper;

    public PersonHelper<AttachEmployee> getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new PersonHelper<AttachEmployee>(getInstance());
        }
        return personHelper;
    }

    private String keyUid;

    private String keySeed;

    private String keyDesKey;

    private Project project;

    private String selectKeyId;

    public String getKeyUid() {
        return keyUid;
    }

    public void setKeyUid(String keyUid) {
        this.keyUid = keyUid;
    }

    public String getKeySeed() {
        return keySeed;
    }

    public void setKeySeed(String keySeed) {
        this.keySeed = keySeed;
    }

    public String getKeyDesKey() {
        return keyDesKey;
    }

    public void setKeyDesKey(String keyDesKey) {
        this.keyDesKey = keyDesKey;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSelectKeyId() {
        return selectKeyId;
    }

    public void setSelectKeyId(String selectKeyId) {
        this.selectKeyId = selectKeyId;
    }

    public void deleteDeveloperKey(){
        Logging.getLog(getClass()).debug("selectKeyId:" + selectKeyId);
        for(DeveloperLogonKey key: getInstance().getDeveloperLogonKeys()){
            if (key.getId().equals(selectKeyId)){
                getInstance().getDeveloperLogonKeys().remove(key);
                update();
                return;
            }
        }
    }

    public String addKey(){
        if (getEntityManager().createQuery("select count(logonKey.id) from DeveloperLogonKey logonKey where logonKey.id = :keyId",Long.class)
                .setParameter("keyId",keyUid).getSingleResult().compareTo(Long.valueOf(0)) <= 0) {
            getInstance().getDeveloperLogonKeys().add(new DeveloperLogonKey(keyUid, keySeed, keyDesKey, getInstance(), project));
            Logging.getLog(getClass()).debug("bindKey:" + keyUid + "," + keySeed + "," + keyDesKey + "," + project.getId() ) ;
            return update();

        }else{
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"KeyIsUsed");
            return null;
        }

    }




    public List<Project> getProjectList(){

            if (!isIdDefined() || !getInstance().getAttachCorporation().getType().equals(AttachCorporation.AttachCorpType.DEVELOPER)) {
               return new ArrayList<Project>(0);
            }else {
                List<Project> result = new ArrayList<Project>(getInstance().getAttachCorporation().getDeveloper().getProjects());
                Collections.sort(result, new Comparator<Project>() {
                    @Override
                    public int compare(Project o1, Project o2) {
                        return o1.getCreateTime().compareTo(o2.getCreateTime());
                    }
                });
                return result;
            }


    }
}

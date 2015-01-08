package com.dgsoft.house.action;

import com.dgsoft.common.PinyinTools;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.HouseSimpleEntityHome;
import com.dgsoft.house.model.Developer;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-11
 * Time: 下午4:42
 */
@Name("developerHome")
public class DeveloperHome extends HouseEntityHome<Developer> {

    private static final String NUMBER_KEY = "DEVELOPER_ID";

    private String searchName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public List<Developer> getSearchResult() {
        if ((searchName == null) || (searchName.trim().equals(""))) {
            TypedQuery<Developer> query = getEntityManager().createQuery("select developer from Developer developer where developer.destroyed = false order by developer.createTime desc", Developer.class);
            query.setMaxResults(5);
            return query.getResultList();
        } else {
            List<Developer> result = getEntityManager().createQuery("select developer from Developer developer where developer.destroyed = false and  ((developer.id = :prefix ) or (lower(developer.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(developer.name) like lower(concat('%',:prefix,'%')))) ", Developer.class).
                    setParameter("prefix", searchName).getResultList();
            return result;
        }
    }

    public void createBySearchName() {
        if (isIdDefined()) {
            clearInstance();
        }
        Logging.getLog(getClass()).debug("create developer by searchName:" + searchName);
        getInstance().setName(searchName);
        nameInputedListener();
    }


    @In
    private FacesMessages facesMessages;

    @Override
    protected boolean verifyRemoveAvailable(){
        if (getEntityManager().createQuery("select coalesce(Count(project.id),0) from Project project where project.developer.id = :developerId",Long.class)
                .setParameter("developerId",getInstance().getId()).getSingleResult() > 0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"Developer_cant_remove");
            return false;
        }
        return true;
    }

    public void nameInputedListener() {
        setPyCode(PinyinTools.getPinyinCode(getInstance().getName()));
    }

    public void setPyCode(String pyCode) {
        if (pyCode == null) {
            getInstance().setPyCode(null);
        } else {
            String value = pyCode.toUpperCase();
            if (value.length() > 100){
               value = pyCode.substring(0,99);
            }
            getInstance().setPyCode(value);
        }
    }

    public String getPyCode() {
        return getInstance().getPyCode();
    }

    private String genId(){
        long number = HouseNumberBuilder.instance().useNumber(NUMBER_KEY);
        while (getEntityManager().find(Developer.class,String.valueOf(number)) != null){
            number = HouseNumberBuilder.instance().useNumber(NUMBER_KEY);
        }
        return String.valueOf(number);
    }

    @Override
    protected Developer createInstance(){
        return  new Developer(genId(),new Date(),false);
    }

    public boolean isEnable() {
        return ! getInstance().isDestroyed();
    }

    public void setEnable(boolean enable) {
        getInstance().setDestroyed(!enable);
    }
}

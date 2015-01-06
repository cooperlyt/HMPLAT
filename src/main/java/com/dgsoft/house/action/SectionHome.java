package com.dgsoft.house.action;

import com.dgsoft.common.PinyinTools;
import com.dgsoft.common.SetLinkList;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.model.Section;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.persistence.NoResultException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-11
 * Time: 下午3:24
 */
@Name("sectionHome")
public class SectionHome extends HouseEntityHome<Section> {

    private static final String NUMBER_KEY = "SECTION_ID";


    @In
    private FacesMessages facesMessages;

//    public void setUniqueName(String name) {
//        try {
//            setId(getEntityManager().createQuery("select section.id from Section section where section.name = :sectionName", String.class).
//                    setParameter("sectionName", name).getSingleResult());
//        } catch (NoResultException e) {
//            clearInstance();
//        }
//    }
//
//    public String getUniqueName() {
//        if (isIdDefined()) {
//            return getInstance().getName();
//        } else {
//            return null;
//        }
//    }

    private String genId(){
        long number = HouseNumberBuilder.instance().useNumber(NUMBER_KEY);
        while (getEntityManager().find(Section.class,String.valueOf(number)) != null){
            number = HouseNumberBuilder.instance().useNumber(NUMBER_KEY);
        }
        return String.valueOf(number);
    }

    @Override
    protected Section createInstance() {
        return new Section(genId(),new Date());
    }


    public void nameInputedListener() {
        setPyCode(PinyinTools.getPinyinCode(getInstance().getName()));
    }

    public void districtSelectListener(){
        if ( (getInstance().getDistrict() != null) && ((getInstance().getAddress() == null) || getInstance().getAddress().trim().equals(""))){
            getInstance().setAddress(getInstance().getDistrict().getName());
        }
    }

    public void setPyCode(String pyCode) {
        if (pyCode == null) {
            getInstance().setPyCode(null);
        } else {
            getInstance().setPyCode(pyCode.toUpperCase());
        }
    }

    public String getPyCode() {
        return getInstance().getPyCode();
    }


    @Override
    protected boolean verifyRemoveAvailable() {
        if (getInstance().getSmsubcompanies().isEmpty() && getInstance().getOwnerGroups().isEmpty() && getInstance().getProjects().isEmpty()) {
            return true;
        }
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "SectionCantRemove");
        return false;

    }
}

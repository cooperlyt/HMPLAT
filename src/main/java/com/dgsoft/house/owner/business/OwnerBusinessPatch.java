package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.*;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.common.system.model.Employee;
import com.dgsoft.common.system.model.Role;
import com.dgsoft.house.owner.action.BuildGridMapHouseSelect;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.Credentials;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cooper on 8/10/15.
 */
@Name("ownerBusinessPatch")
public class OwnerBusinessPatch {

    private static final String BUSINESS_PATCH_EDIT_PAGE = "/business/houseOwner/BusinessPatchSubscribe.xhtml";
    private static final String PATCH_BUSINESS_FILE_PAGE = "/business/houseOwner/BusinessPatchFileUpload.xhtml";
    private static final String PATCH_BUSINESS_CONFIRM_PAGE = "/business/houseOwner/BusinessPatchConfirm.xhtml";


    @In
    private Credentials credentials;

    @In(create = true)
    private EntityManager systemEntityManager;

    @In(required = false)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In("#{messages.datetimepattern}")
    private String dateFormat;

    @In
    private AuthenticationInfo authInfo;

    @In
    private FacesMessages facesMessages;

    @In(required = false)
    private BuildGridMapHouseSelect buildGridMapHouseSelect;

    private List<FilterBusinessCategory> filterBusinessCategories;

    private String selectCategoryId;

    private RecordStore recordStore = new RecordStore();

    public String getSelectCategoryId() {
        return selectCategoryId;
    }

    public void setSelectCategoryId(String selectCategoryId) {
        this.selectCategoryId = selectCategoryId;
    }

    public FilterBusinessCategory getSelectCategory() {
        for (FilterBusinessCategory category : filterBusinessCategories) {
            if (category.getCategory().getId().equals(selectCategoryId)) {
                return category;
            }
        }
        return null;
    }

    public RecordStore getRecordStore() {
        return recordStore;
    }

    public void setRecordStore(RecordStore recordStore) {
        this.recordStore = recordStore;
    }

    public void intiBusinessCategory() {


        Set<BusinessDefine> businessDefines = new HashSet<BusinessDefine>();
        Set<BusinessCategory> businessCategorys = new HashSet<BusinessCategory>();


        List<Role> roles;
        if (AuthenticationManager.SUPER_ADMIN_USER_NAME.equals(credentials.getUsername())) {
            roles = systemEntityManager.createQuery("select role from Role role", Role.class).getResultList();
        } else {
            roles = new ArrayList<Role>(systemEntityManager.find(Employee.class, credentials.getUsername()).getRoles());
        }

        for (Role role : roles) {
            for (BusinessDefine define : role.getOldBusinessDefines()) {
                if (define.isEnable()) {
                    businessDefines.add(define);
                    businessCategorys.add(define.getBusinessCategory());
                }
            }
        }

        filterBusinessCategories = new ArrayList<FilterBusinessCategory>();
        for (BusinessCategory category : businessCategorys) {

            FilterBusinessCategory filterBusinessCategory = new FilterBusinessCategory(category);
            filterBusinessCategories.add(filterBusinessCategory);

            for (BusinessDefine businessDefine : category.getBusinessDefines()) {
                if (businessDefines.contains(businessDefine)) {
                    filterBusinessCategory.putDefine(businessDefine);
                }
            }
        }

        Collections.sort(filterBusinessCategories, new Comparator<FilterBusinessCategory>() {
            @Override
            public int compare(FilterBusinessCategory o1, FilterBusinessCategory o2) {
                return Integer.valueOf(o1.getCategory().getPriority()).compareTo(o2.getCategory().getPriority());
            }
        });

        if (selectCategoryId == null && !filterBusinessCategories.isEmpty()) {
            selectCategoryId = filterBusinessCategories.get(0).getCategory().getId();
        }

    }

    public List<FilterBusinessCategory> getFilterBusinessCategories() {

        if (filterBusinessCategories == null) {
            intiBusinessCategory();
        }

        return filterBusinessCategories;
    }


    public String singleHouseSelected() {

        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        ownerBusinessHome.getInstance().setApplyTime(null);
        BusinessHouse startHouse = new BusinessHouse(buildGridMapHouseSelect.getSelectBizHouse());

        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), startHouse));

        return beginEdit();

    }

    private String getInfoCompletePath() {

        if (RunParam.instance().getBooleanParamValue("CreateUploadFile")) {
            return PATCH_BUSINESS_FILE_PAGE;
        } else {
            return PATCH_BUSINESS_CONFIRM_PAGE;
        }
    }


    public String infoComplete() {

        if (businessDefineHome.isHaveNextEditGroup()) {
            businessDefineHome.nextEditGroup();
            return BUSINESS_PATCH_EDIT_PAGE;
        } else {
            if (businessDefineHome.saveEditSubscribes()) {
                return getInfoCompletePath();
            } else {
                return BUSINESS_PATCH_EDIT_PAGE;
            }

        }

    }

    public String beginEdit() {
        if (businessDefineHome.isHaveEditSubscribe()) {
            businessDefineHome.firstEditGroup();
            return BUSINESS_PATCH_EDIT_PAGE;
        } else {
            return getInfoCompletePath();
        }
    }

    public Date getMaxDateTime() {
        Date result = new Date();

        for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
            try {
                Date normalBizDate = ownerBusinessHome.getEntityManager().createQuery("select min(houseBusiness.ownerBusiness.applyTime) from HouseBusiness houseBusiness where houseBusiness.ownerBusiness.status <> 'ABORT' and houseBusiness.ownerBusiness.source = 'BIZ_CREATE' and houseBusiness.houseCode = :houseCode", Date.class).setParameter("houseCode", houseBusiness.getHouseCode()).getSingleResult();
                if (normalBizDate != null && normalBizDate.compareTo(result) < 0) {
                    result = normalBizDate;
                }
            } catch (NoResultException e) {

            }
        }

        return result;

    }

    public String getLocalMaxDateTime() {
        return new SimpleDateFormat(dateFormat).format(getMaxDateTime());
    }

    @End
    @Transactional
    public String completeAndSave() {

        if (getMaxDateTime().compareTo(ownerBusinessHome.getInstance().getApplyTime()) < 0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"PatchDateTimeError",getLocalMaxDateTime());
            return null;

        }

        ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_AFTER_SAVE);
        ownerBusinessHome.getInstance().setRecorded(true);
        ownerBusinessHome.getInstance().setRegTime(ownerBusinessHome.getInstance().getApplyTime());
        ownerBusinessHome.getInstance().setRecordTime(ownerBusinessHome.getInstance().getApplyTime());

        ownerBusinessHome.getInstance().getBusinessEmps().add(new BusinessEmp(ownerBusinessHome.getInstance(), BusinessEmp.EmpType.PATCH_EMP,authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName(),new Date()));

        ownerBusinessHome.getInstance().setStatus(BusinessInstance.BusinessStatus.COMPLETE);

        recordStore.setOwnerBusiness(ownerBusinessHome.getInstance());
        recordStore.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());

        for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {
            houseBusiness.setRecordStore(recordStore);
            if (ownerBusinessHome.getEntityManager().createQuery("select count(houseBusiness.id) from HouseBusiness houseBusiness where houseBusiness.ownerBusiness.status <> 'ABORT' and houseBusiness.ownerBusiness.source <> 'BIZ_AFTER_SAVE' and houseBusiness.houseCode = :houseCode", Long.class).setParameter("houseCode", houseBusiness.getHouseCode()).getSingleResult().compareTo(Long.valueOf(0)) <= 0) {

                HouseRecord houseRecord = ownerBusinessHome.getEntityManager().find(HouseRecord.class, houseBusiness.getHouseCode());
                if (houseRecord == null) {
                    houseBusiness.getAfterBusinessHouse().setHouseRecord(new HouseRecord(houseBusiness.getAfterBusinessHouse()));
                } else {
                    try {
                        Date maxPatchDate = ownerBusinessHome.getEntityManager().createQuery("select max(houseBusiness.ownerBusiness.applyTime) from HouseBusiness houseBusiness where houseBusiness.ownerBusiness.status in ('COMPLETE', 'MODIFYING' , 'COMPLETE_CANCEL')  and houseBusiness.houseCode =:houseCode and houseBusiness.ownerBusiness.source = 'BIZ_AFTER_SAVE' ", Date.class).setParameter("houseCode", houseBusiness.getHouseCode()).getSingleResult();
                        if (maxPatchDate != null &&  (maxPatchDate.compareTo(ownerBusinessHome.getInstance().getApplyTime()) > 0)) {
                            houseRecord.setBusinessHouse(houseBusiness.getAfterBusinessHouse());
                            houseBusiness.getAfterBusinessHouse().setHouseRecord(houseRecord);
                        }
                    } catch (NoResultException e) {

                    }
                }

            }

        }


        for (BusinessProject businessProject : ownerBusinessHome.getInstance().getBusinessProjects()) {
            businessProject.setRecordStore(recordStore);

        }
        return ownerBusinessHome.persist();

    }


}

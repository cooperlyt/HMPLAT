package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationManager;
import com.dgsoft.common.system.FilterBusinessCategory;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.common.system.model.Employee;
import com.dgsoft.common.system.model.Role;
import com.dgsoft.house.owner.action.BuildGridMapHouseSelect;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import org.jboss.seam.security.Credentials;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by cooper on 8/10/15.
 */
@Name("ownerBusinessPatch")
public class OwnerBusinessPatch {

    @In
    private Credentials credentials;

    @In(create = true)
    private EntityManager systemEntityManager;

    @In(create = true)
    private EntityManager ownerEntityManager;

    @In
    private FacesMessages facesMessages;

    @In(required = false)
    private BuildGridMapHouseSelect buildGridMapHouseSelect;

    private List<FilterBusinessCategory> filterBusinessCategories;

    private String selectCategoryId;

    public String getSelectCategoryId() {
        return selectCategoryId;
    }

    public void setSelectCategoryId(String selectCategoryId) {
        this.selectCategoryId = selectCategoryId;
    }

    public FilterBusinessCategory getSelectCategory(){
        for(FilterBusinessCategory category: filterBusinessCategories){
            if (category.getCategory().getId().equals(selectCategoryId)){
                return category;
            }
        }
        return null;
    }



    public void intiBusinessCategory(){


        Set<BusinessDefine> businessDefines = new HashSet<BusinessDefine>();
        Set<BusinessCategory> businessCategorys = new HashSet<BusinessCategory>();


        List<Role> roles;
        if (AuthenticationManager.SUPER_ADMIN_USER_NAME.equals(credentials.getUsername())){
            roles = systemEntityManager.createQuery("select role from Role role", Role.class).getResultList();
        }else{
            roles = new ArrayList<Role>(systemEntityManager.find(Employee.class, credentials.getUsername()).getRoles());
        }

        for(Role role : roles){
            for(BusinessDefine define: role.getOldBusinessDefines()){
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

        if (selectCategoryId == null && !filterBusinessCategories.isEmpty()){
            selectCategoryId = filterBusinessCategories.get(0).getCategory().getId();
        }

    }

    public List<FilterBusinessCategory> getFilterBusinessCategories() {

        if (filterBusinessCategories == null){
            intiBusinessCategory();
        }

        return filterBusinessCategories;
    }





}

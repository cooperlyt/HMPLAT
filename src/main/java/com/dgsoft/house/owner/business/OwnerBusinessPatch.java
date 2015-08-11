package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.FilterBusinessCategory;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.common.system.model.Employee;
import com.dgsoft.common.system.model.Role;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 8/10/15.
 */
@Name("ownerBusinessPatch")
@Scope(ScopeType.CONVERSATION)
public class OwnerBusinessPatch {

    @In
    private Credentials credentials;

    @In
    private SystemEntityLoader systemEntityLoader;

    private FilterBusinessCategory filterBusinessCategory;

    public void intiBusinessCategory(){


        Set<BusinessDefine> businessDefines = new HashSet<BusinessDefine>();
        Set<BusinessCategory> bussinessCategorys = new HashSet<BusinessCategory>();

        for(Role role :systemEntityLoader.getEntityManager().find(Employee.class,credentials.getUsername()).getRoles()){
            for(BusinessDefine define: role.getOldBusinessDefines()){
                if (define.isEnable()) {
                    businessDefines.add(define);
                    bussinessCategorys.add(define.getBusinessCategory());
                }
            }
        }




    }


}

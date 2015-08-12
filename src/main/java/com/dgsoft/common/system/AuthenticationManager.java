package com.dgsoft.common.system;

import com.dgsoft.common.MD5Util;
import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 5/2/13
 * Time: 9:41 AM
 */


@Name("authenticationManager")
public class AuthenticationManager {

    public static final String SUPER_ADMIN_USER_NAME = "root";

    @Logger
    private Log log;

    @In(create = true)
    private EntityManager systemEntityManager;

    @In
    private Identity identity;

    @In
    private Actor actor;

    @Out(scope = ScopeType.SESSION)
    private AuthenticationInfo authInfo;


    @In("#{messages.superAdministrator}")
    private String superAdminName;

    public boolean authenticate() {
        authInfo = new AuthenticationInfo();
        try {

            Set<Role> roles = new HashSet<Role>();

            Employee loginEmployee;
            if (SUPER_ADMIN_USER_NAME.equals(identity.getCredentials().getUsername()) && identity.getCredentials().getPassword().equals("dgsoft")) {
                loginEmployee = new Employee("Root","root");

                roles.addAll(systemEntityManager.createQuery("select r from Role r").getResultList());

            } else {
                loginEmployee = systemEntityManager.createQuery("select e from Employee e where e.id = :username",Employee.class)
                        .setParameter("username", identity.getCredentials().getUsername()).getSingleResult();

                log.info("loginEmployee:" + (loginEmployee != null ? identity.getCredentials().getPassword() + "==" + loginEmployee.getPassword() + "|enable:" + loginEmployee.isEnable() : "null"));


                if (loginEmployee == null || !loginEmployee.getPassword().equals(MD5Util.makeMD5(identity.getCredentials().getPassword())) || !loginEmployee.isEnable()) {
                    return false;
                }
                roles.addAll(loginEmployee.getRoles());
            }

            List<Role> funcRoles = new ArrayList<Role>();
            actor.setId(identity.getCredentials().getUsername());
            log.info("user login:" + loginEmployee.getId() + "role:" + roles.size());
            for (Role role : roles) {
                identity.addRole(role.getId());
                log.info("add role:" + role.getId());
                actor.getGroupActorIds().add(role.getId());
                if (!role.getFunctions().isEmpty()){
                    funcRoles.add(role);
                }
            }

            Collections.sort(funcRoles, OrderBeanComparator.getInstance());
            authInfo.setFunctionRoleList(funcRoles);
            //authInfo.generateFuncCategorys();
            authInfo.setLoginEmployee(loginEmployee);
            generateBusinessCategorys(roles);
            return true;
        } catch (NoResultException ex) {
            log.debug("NoResultException", ex);
            return false;
        }

    }

    private void generateBusinessCategorys(Set<Role> roles) {
        Set<BusinessDefine> businessDefines = new HashSet<BusinessDefine>();
        Set<BusinessCategory> bussinessCategorys = new HashSet<BusinessCategory>();
        for (Role role : roles) {
            for (BusinessDefine define: role.getBusinessDefines()){
                if (define.isEnable()){
                    businessDefines.add(define);
                }
            }

        }
        for (BusinessDefine businessDefine : businessDefines) {
            bussinessCategorys.add(businessDefine.getBusinessCategory());
        }

        List<FilterBusinessCategory> result = new ArrayList<FilterBusinessCategory>();
        for (BusinessCategory category : bussinessCategorys) {

            FilterBusinessCategory filterBusinessCategory = new FilterBusinessCategory(category);
            result.add(filterBusinessCategory);

            for (BusinessDefine businessDefine : category.getBusinessDefines()) {
                if (businessDefines.contains(businessDefine)) {
                    filterBusinessCategory.putDefine(businessDefine);
                }
            }
        }
        Collections.sort(result, new Comparator<FilterBusinessCategory>() {
            @Override
            public int compare(FilterBusinessCategory o1, FilterBusinessCategory o2) {
                return new Integer(o1.getCategory().getPriority()).compareTo(o2.getCategory().getPriority());
            }
        });
        authInfo.setAuthenticationBussinessCategorys(result);
    }
}

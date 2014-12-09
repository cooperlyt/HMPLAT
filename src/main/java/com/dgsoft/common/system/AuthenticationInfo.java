package com.dgsoft.common.system;

import com.dgsoft.common.system.model.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 8/22/13
 * Time: 3:50 PM
 */
public class AuthenticationInfo implements java.io.Serializable {

    private List<BusinessCategory> authenticationBussinessCategorys = new ArrayList<BusinessCategory>();

    private Role currRole;

    private List<Role> functionRoleList;

    private Employee loginEmployee;

    public Employee getLoginEmployee() {
        return loginEmployee;
    }

    public void setLoginEmployee(Employee loginEmployee) {
        this.loginEmployee = loginEmployee;
    }

    public Role getCurrRole() {
        if (currRole == null) {
            if (!functionRoleList.isEmpty())
                currRole = functionRoleList.get(0);
        }
        return currRole;
    }

    public void setCurrRole(Role currRole) {
        this.currRole = currRole;
    }


    public List<BusinessCategory> getAuthenticationBussinessCategorys() {
        return authenticationBussinessCategorys;
    }

    public void setAuthenticationBussinessCategorys(List<BusinessCategory> authenticationBussinessCategorys) {
        this.authenticationBussinessCategorys = authenticationBussinessCategorys;
    }

    private List<Function> getFunctionsByCategory(Function.FunctionCategory category) {
        List<Function> result = new ArrayList<Function>();
        if (getCurrRole() != null) {
            for (Function function : getCurrRole().getFunctions()) {
                if (function.getFuncCategory().equals(category)) {
                    result.add(function);
                }
            }
        }
        Collections.sort(result,new Comparator<Function>() {
            @Override
            public int compare(Function o1, Function o2) {
                return Integer.valueOf(o1.getPriority()).compareTo(o2.getPriority());
            }
        });
        return result;
    }

    public List<Function> getDataFunctions(){
        return getFunctionsByCategory(Function.FunctionCategory.DATA_MGR);
    }

    public List<Function> getTotalFunctions(){
        return getFunctionsByCategory(Function.FunctionCategory.TOTAL_REPORT);
    }

    public List<Function> getWorkFunctions(){
        return getFunctionsByCategory(Function.FunctionCategory.DAY_WORK);
    }

//    public void generateFuncCategorys() {
//
//        authenticationFuncCategorys.clear();
//
//        Map<String, FuncCategory> result = new HashMap<String, FuncCategory>();
//
//        Collection<Function> showFunctions = new HashSet<Function>();
//
//        if (currRole == null) {
//            for (Role role : functionRoleList) {
//                showFunctions.addAll(role.getFunctions());
//            }
//        } else {
//            showFunctions = currRole.getFunctions();
//        }
//
//        for (Function function : showFunctions) {
//
//            FuncCategory curCategory = result.get(function.getFuncCategory().getId());
//
//            if (curCategory == null) {
//                curCategory = new FuncCategory(function.getFuncCategory().getId(), function.getFuncCategory().getName());
//                result.put(curCategory.getId(), curCategory);
//            }
//
//            curCategory.getFunctions().add(function);
//
//        }
//
//
//        authenticationFuncCategorys.addAll(result.values());
//        Collections.sort(authenticationFuncCategorys, OrderBeanComparator.getInstance());
//    }

    public List<Role> getFunctionRoleList() {
        return functionRoleList;
    }

    public void setFunctionRoleList(List<Role> functionRoleList) {
        this.functionRoleList = functionRoleList;
    }

    public String getCurrRoleCategoryId() {
        if (currRole != null) {
            return currRole.getId();
        }
        return null;
    }

    public void setCurrRoleCategoryId(String roleCategoryId) {
        for (Role role : functionRoleList) {
            if (role.getId().equals(roleCategoryId)) {
                currRole = role;
                return;
            }
        }
        currRole = null;
    }

}

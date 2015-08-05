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

    private List<FilterBusinessCategory> authenticationBussinessCategorys = new ArrayList<FilterBusinessCategory>();

    private Role currRole;

    private FilterBusinessCategory currBusinessCategory;

    private List<Role> functionRoleList;

    private Employee loginEmployee;

    public Employee getLoginEmployee() {
        return loginEmployee;
    }

    public void setLoginEmployee(Employee loginEmployee) {
        this.loginEmployee = loginEmployee;
    }

    public FilterBusinessCategory getCurrBusinessCategory() {
        return currBusinessCategory;
    }

    public String getCurrBusinessCategoryId(){
        if (currBusinessCategory == null){
            return null;
        }else{
            return currBusinessCategory.getCategory().getId();
        }
    }

    public void setCurrBusinessCategoryId(String id){
        if ((id == null) || id.trim().equals("")){
            currBusinessCategory = null;
        }else{
            for (FilterBusinessCategory category: authenticationBussinessCategorys){
                if (id.equals(category.getCategory().getId())){
                    currBusinessCategory = category;
                    return;
                }
            }
            currBusinessCategory = null;
        }
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


    public List<FilterBusinessCategory> getAuthenticationBussinessCategorys() {
        return authenticationBussinessCategorys;
    }

    public void setAuthenticationBussinessCategorys(List<FilterBusinessCategory> authenticationBussinessCategorys) {
        this.authenticationBussinessCategorys = authenticationBussinessCategorys;
        Collections.sort(authenticationBussinessCategorys, new Comparator<FilterBusinessCategory>() {
            @Override
            public int compare(FilterBusinessCategory o1, FilterBusinessCategory o2) {
                return Integer.valueOf(o1.getCategory().getPriority()).compareTo(o2.getCategory().getPriority());
            }
        });
        if (! authenticationBussinessCategorys.isEmpty()){
            currBusinessCategory = authenticationBussinessCategorys.get(0);
        }
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

    public boolean isHasCreateRole(String businessDefineId){
        for(FilterBusinessCategory category: authenticationBussinessCategorys){
            for(BusinessDefine define: category.getDefineList()){
                if (define.getId().equals(businessDefineId)){
                    return true;
                }
            }
        }
        return false;
    }

}

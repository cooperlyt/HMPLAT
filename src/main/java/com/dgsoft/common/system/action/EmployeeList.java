package com.dgsoft.common.system.action;

import com.dgsoft.common.system.model.Employee;
import com.dgsoft.house.HouseEntityQuery;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 7/3/15.
 */
@Name("employeeList")
public class EmployeeList extends HouseEntityQuery<Employee>{


    private static final String EJBQL = "select employee from Employee employee";

    private static final String[] RESTRICTIONS = {
            "lower(employee.id) like lower(concat(#{employeeList.searchKey},'%'))",
            "lower(employee.personName) like lower(concat(#{employeeList.searchKey},'%'))",};

    private String searchKey;

    public EmployeeList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }


}

package com.dgsoft.common.system.action;

import com.dgsoft.common.system.SystemEntityQuery;
import com.dgsoft.common.system.model.Employee;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cooper on 7/3/15.
 */
@Name("employeeList")
public class EmployeeList extends SystemEntityQuery<Employee> {


    private static final String EJBQL = "select employee from Employee employee";

    private static final String[] RESTRICTIONS = {
            "lower(employee.id) like lower(concat('%',concat(#{employeeList.searchKey},'%')))",
            "lower(employee.personName) like lower(concat('%',concat(#{employeeList.searchKey},'%')))",};

    private static final String[] SORT_COLUMNS = {
            "employee.joinDate", "employee.id", "employee.personName"
    };

    private String searchKey;

    public EmployeeList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setMaxResults(25);
        setRestrictionLogicOperator("or");
        setOrderColumn(SORT_COLUMNS[0]);
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<String> getSortColumns() {
        return Arrays.asList(SORT_COLUMNS);
    }


}

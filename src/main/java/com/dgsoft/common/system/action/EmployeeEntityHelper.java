package com.dgsoft.common.system.action;

import com.dgsoft.common.EntityHomeAdapter;
import com.dgsoft.common.system.PersonEntityHelper;
import com.dgsoft.common.system.action.EmployeeHome;
import com.dgsoft.common.system.model.Employee;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/21/14.
 */
@Name("employeeEntityHelper")
public class EmployeeEntityHelper extends PersonEntityHelper<Employee> {

    @In
    private EmployeeHome employeeHome;

    @Override
    protected EntityHomeAdapter<Employee> getEntityHome() {
        return employeeHome;
    }


}

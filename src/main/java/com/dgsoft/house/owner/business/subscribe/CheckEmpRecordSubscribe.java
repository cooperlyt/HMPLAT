package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.Employee;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessEmp;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-20.
 * 档案补录审核人
 */
@Name("checkEmpRecordSubscribe")
public class CheckEmpRecordSubscribe extends OwnerEntityHome<BusinessEmp> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;


    private boolean have;

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeId(){
        if (employee == null){
            return null;
        }
        return employee.getId();
    }

    public void setEmployeeId(String id){
        if (id == null || id.trim().equals("")){
            employee = null;
        }else if (employee ==null || !id.equals(employee.getId())){
            employee = systemEntityLoader.getEntityManager().find(Employee.class,id);
        }
    }




    @Override
    public void create()
    {
        super.create();

        for(BusinessEmp businessEmp:ownerBusinessHome.getInstance().getBusinessEmps()){
            if (businessEmp.getType().equals(BusinessEmp.EmpType.CHECK_EMP)){
                setId(businessEmp.getId());
                have = true;
                break;
            }else{
                have=false;
            }
        }
    }

    @Override
    public BusinessEmp createInstance(){
        BusinessEmp result = new BusinessEmp(BusinessEmp.EmpType.CHECK_EMP);

        return result;
    }

    public void checkHave(){
        clearInstance();
        if (have){
            if (employee !=null){
                getInstance().setEmpCode(employee.getPyCode());
                getInstance().setEmpName(employee.getPersonName());

            }
            ownerBusinessHome.getInstance().getBusinessEmps().add(getInstance());
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        } else {
            ownerBusinessHome.getInstance().getBusinessEmps().add(null);
            getInstance().setOwnerBusiness(null);
        }
    }



}

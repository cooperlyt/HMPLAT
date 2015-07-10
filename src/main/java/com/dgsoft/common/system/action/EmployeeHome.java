package com.dgsoft.common.system.action;

import com.dgsoft.common.MD5Util;
import com.dgsoft.common.PinyinTools;
import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.model.Employee;
import com.dgsoft.common.system.model.Role;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/9/13
 * Time: 10:29 AM
 */
@Name("employeeHome")
public class EmployeeHome extends SystemEntityHome<Employee> {

    private static final String DEFAULT_PASSWORD = "111111";


    @In
    private FacesMessages facesMessages;

    private List<Role> selectedRoles = new ArrayList<Role>();

    public List<Role> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<Role> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    @Override
    public Employee createInstance() {
        return new Employee(new Date(), MD5Util.makeMD5(DEFAULT_PASSWORD));
    }


    public void verifyIdAvailable(ValueChangeEvent e) {
        String id = (String) e.getNewValue();
        if (!isIdAvailable(id)) {
            log.info("add confirm message");
            facesMessages.addToControlFromResourceBundle(e.getComponent().getId(), StatusMessage.Severity.ERROR, "fieldConflict", id);
        }
    }


    @Override
    protected boolean verifyPersistAvailable() {

        String id = this.getInstance().getId();
        if (!isIdAvailable(id)) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "fieldConflict", id);
            return false;
       } else
            return true;
    }


    public boolean isIdAvailable(String id) {
        return getEntityManager().createQuery("select emp from Employee emp where emp.id = ?1").setParameter(1, id).getResultList().size() == 0;
    }


    public void readPower() {
        selectedRoles.clear();
        selectedRoles.addAll(getInstance().getRoles());
    }

    public String savePowerAssign() {
        getInstance().getRoles().clear();
        getInstance().getRoles().addAll(selectedRoles);

        return super.update();
    }

    public String resetPassword(){
        getInstance().setPassword(MD5Util.makeMD5(DEFAULT_PASSWORD));
        return update();
    }

    public void nameInputedListener() {
        setPyCode(PinyinTools.getPinyinCode(getInstance().getPersonName()));
    }

    public void setPyCode(String pyCode) {
        if (pyCode == null) {
            getInstance().setPyCode(null);
        } else {
            String value = pyCode.toUpperCase();
            if (value.length() > 100){
                value = pyCode.substring(0,99);
            }
            getInstance().setPyCode(value);
        }
    }

    public String getPyCode() {
        return getInstance().getPyCode();
    }


}

package com.dgsoft.common.system.action;

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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/9/13
 * Time: 10:29 AM
 */
@Name("employeeHome")
public class EmployeeHome extends SystemEntityHome<Employee> {


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
        return new Employee();
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


    @End
    public String savePowerAssign() {
        getInstance().getRoles().clear();
        getInstance().getRoles().addAll(selectedRoles);

        return super.update();
    }


}

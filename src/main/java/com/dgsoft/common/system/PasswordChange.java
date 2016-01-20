package com.dgsoft.common.system;

import com.dgsoft.common.MD5Util;
import com.dgsoft.common.system.model.Employee;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.security.Credentials;

/**
 * Created by cooper on 1/20/16.
 */
@Name("passwordChange")
public class PasswordChange {

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    @In
    private Credentials credentials;

    @In
    private FacesMessages facesMessages;

    private String oldPassword;

    private String password1;

    private String password2;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void changePassword(){
        if (oldPassword == null || password1 == null || password2 == null){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"changePasswordNull");
            return;
        }

        if (!password1.equals(password2)){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"changePasswordNotSame");
            return;
        }

        Employee employee = systemEntityLoader.getEntityManager().find(Employee.class, credentials.getUsername());


        if (!MD5Util.makeMD5(oldPassword).equals(employee.getPassword())){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"changeOldPasswordError");
            return;
        }

        employee.setPassword(MD5Util.makeMD5(password1));

        systemEntityLoader.getEntityManager().flush();
        facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO,"passwordChanged");

        oldPassword = null;
        password1 = null;
        password2 = null;

    }

}

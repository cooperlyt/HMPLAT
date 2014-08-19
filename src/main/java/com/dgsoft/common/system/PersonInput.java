package com.dgsoft.common.system;

import com.dgsoft.common.system.action.PersonHome;
import com.dgsoft.common.system.model.Person;
import com.dgsoft.common.system.model.PersonId;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Log;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 9/2/13
 * Time: 12:02 PM
 */
@Name("personInput")
public class PersonInput {

    private String credentialsNumber;

    private PersonId.CredentialsType cerdentialsType;

    @In(create = true)
    private PersonHome personHome;

    @Logger
    protected Log log;

    @In
    private FacesMessages facesMessages;

    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    public void setCredentialsNumber(String credentialsNumber) {
        this.credentialsNumber = credentialsNumber;
    }

    public PersonId.CredentialsType getCerdentialsType() {
        return cerdentialsType;
    }

    public void setCerdentialsType(PersonId.CredentialsType cerdentialsType) {
        this.cerdentialsType = cerdentialsType;
    }

    public void credentialsChangeListener() {


        if ((cerdentialsType == null) || (credentialsNumber == null) || (credentialsNumber.trim().equals("")))
            return;



        if (cerdentialsType != PersonId.CredentialsType.OTHER) {

            List<Person> persons = personHome.getEntityManager().createQuery("select person from Person person where person.credentialsNumber =?1 and person.credentialsType = ?2").setParameter(1, credentialsNumber).setParameter(2, cerdentialsType).getResultList();
            if (!persons.isEmpty()) {
                personHome.setId(persons.get(0).getId());
                facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "person_read_from_database", personHome.getInstance().getName());
                return;
            }
        }

        //if (personHome.isManaged())
            //personHome.clearInstance();
        personHome.setId("");
        personHome.getInstance().setId(new PersonId(cerdentialsType,credentialsNumber));

    }

}

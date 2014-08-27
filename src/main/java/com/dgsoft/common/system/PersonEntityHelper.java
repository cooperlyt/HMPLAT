package com.dgsoft.common.system;

import com.dgsoft.common.EntityHomeAdapter;
import com.dgsoft.common.system.action.PersonHome;
import com.dgsoft.common.system.model.Person;
import com.dgsoft.common.system.model.PersonId;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityNotFoundException;
import org.jboss.seam.log.Logging;

/**
 * Created by cooper on 8/21/14.
 */
@Scope(ScopeType.CONVERSATION)
public abstract class PersonEntityHelper<E extends PersonEntity> {

    protected abstract EntityHomeAdapter<E> getEntityHome();

    private PersonHome getPersonHome(){
       return (PersonHome) Component.getInstance("personHome",true,true);
    }



    public PersonEntity.CredentialsType getCredentialsType() {
        return getEntityHome().getInstance().getCredentialsType();
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        getEntityHome().getInstance().setCredentialsType(credentialsType);
    }

    public String getCredentialsNumber() {
        return getEntityHome().getInstance().getCredentialsNumber();
    }

    public void setCredentialsNumber(String credentialsNumber) {
        getEntityHome().getInstance().setCredentialsNumber(credentialsNumber);
    }

    public String getPersonName() {
        return getEntityHome().getInstance().getPersonName();
    }

    public void setPersonName(String name) {
        getEntityHome().getInstance().setPersonName(name);
    }

    private boolean isManager = false;

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void readFromCard() {

        isManager = true;
    }

    protected void fillPerson(Person person){
        getEntityHome().getInstance().setPersonName(person.getName());
    }

    public void typeChange() {
        isManager = false;
        if ((getCredentialsType() != null) &&  getCredentialsType().equals(PersonEntity.CredentialsType.OTHER)) {
            setCredentialsNumber(NumberBuilder.instance().getSampleNumber("OTHER_PERSON_CARD_NO"));
        } else {
            setCredentialsNumber(null);
        }

    }

    public void numberChange() {
        isManager = false;
        if ((getCredentialsNumber() != null) &&
                !getCredentialsType().equals(PersonEntity.CredentialsType.OTHER) &&
                !getCredentialsNumber().equals("")){
            Person person = getPersonHome().getEntityManager().find(Person.class, new PersonId(getCredentialsType(), getCredentialsNumber()));
            if (person != null) {
                fillPerson(person);
                isManager = true;
            }
        }
    }


    public void clear() {

        getPersonHome().clearInstance();
        isManager = false;
    }


}

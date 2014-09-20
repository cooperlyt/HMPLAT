package com.dgsoft.common.system;

import com.dgsoft.common.system.model.Person;
import com.dgsoft.common.system.model.PersonId;
import org.jboss.seam.Component;
import org.jboss.seam.log.Logging;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by cooper on 9/19/14.
 */
public class PersonHelper<E extends PersonEntity>  {
    public PersonHelper() {
        isManager = false;
    }

    public PersonHelper(E entity) {
        this.entity = entity;
        isManager = false;
    }

    private E entity;

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
        isManager = false;
    }


    public PersonEntity.CredentialsType getCredentialsType() {
        return getEntity().getCredentialsType();
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        getEntity().setCredentialsType(credentialsType);
    }

    public String getCredentialsNumber() {
        return getEntity().getCredentialsNumber();
    }

    public void setCredentialsNumber(String credentialsNumber) {
        getEntity().setCredentialsNumber(credentialsNumber);
    }

    public String getPersonName() {
        return getEntity().getPersonName();
    }

    public void setPersonName(String name) {
        getEntity().setPersonName(name);
    }


    private boolean isManager = false;

    public boolean isManager() {
        return isManager;
    }

    protected void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void readFromCard() {

        isManager = true;
    }

    protected void fillPerson(Person person){
        setPersonName(person.getName());
    }

    public void typeChange() {
        isManager = false;
        if ((getCredentialsType() != null) &&  getCredentialsType().equals(PersonEntity.CredentialsType.OTHER)) {
            setCredentialsNumber(NumberBuilder.instance().getSampleNumber("OTHER_PERSON_CARD_NO"));
        } else {
            setCredentialsNumber(null);
        }

    }

    private EntityManager getSystemEntityManager(){
        return (EntityManager) Component.getInstance("systemEntityManager",true,true);
    }

    public void numberChange() {
        isManager = false;
        if ((getCredentialsNumber() != null) &&
                !getCredentialsType().equals(PersonEntity.CredentialsType.OTHER) &&
                !getCredentialsNumber().equals("")){
            Person person = getSystemEntityManager().find(Person.class, new PersonId(getCredentialsType(), getCredentialsNumber()));
            if (person != null) {
                fillPerson(person);
                isManager = true;
            }
        }
    }


}

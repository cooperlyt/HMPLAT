package com.dgsoft.common.system;

import com.dgsoft.common.system.model.Person;
import com.dgsoft.common.system.model.PersonId;
import org.jboss.seam.Component;

import javax.persistence.EntityManager;

/**
 * Created by cooper on 9/19/14.
 */
public class PersonHelper<E extends PersonEntity>  {


    private E entity;

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }


    public PersonEntity.CredentialsType getCredentialsType() {
        return entity.getCredentialsType();
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        entity.setCredentialsType(credentialsType);
    }

    public String getCredentialsNumber() {
        return entity.getCredentialsNumber();
    }

    public void setCredentialsNumber(String credentialsNumber) {
        entity.setCredentialsNumber(credentialsNumber);
    }

    public String getPersonName() {
        return entity.getPersonName();
    }

    public void setPersonName(String name) {
        entity.setPersonName(name);
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


    public void clear() {
       // entity = new
        isManager = false;
    }


}

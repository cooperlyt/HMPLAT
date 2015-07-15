package com.dgsoft.common.system;

import com.dgsoft.common.system.model.Person;
import com.dgsoft.common.system.model.PersonId;
import org.jboss.seam.Component;
import org.jboss.seam.log.Logging;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.UUID;

/**
 * Created by cooper on 9/19/14.
 */
public class PersonHelper<E extends PersonEntity>  {

    private String uuid;


    public PersonHelper(E entity) {
        this.entity = entity;
        isManager = false;
        uuid = UUID.randomUUID().toString();
    }

    private E entity;

    public E getPersonEntity() {
        return entity;
    }

    public PersonEntity.CredentialsType getCredentialsType() {
        return getPersonEntity().getCredentialsType();
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        getPersonEntity().setCredentialsType(credentialsType);
    }

    public String getCredentialsNumber() {
        return getPersonEntity().getCredentialsNumber();
    }

    public void setCredentialsNumber(String credentialsNumber) {
        getPersonEntity().setCredentialsNumber(credentialsNumber);
    }

    public String getPersonName() {
        return getPersonEntity().getPersonName();
    }

    public void setPersonName(String name) {
        getPersonEntity().setPersonName(name);
    }

    public String getUuid() {
        return uuid;
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

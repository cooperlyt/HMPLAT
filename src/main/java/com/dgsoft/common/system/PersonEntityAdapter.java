package com.dgsoft.common.system;

/**
 * Created by cooper on 9/20/14.
 */
public class PersonEntityAdapter<E extends PersonEntity> {

    private PersonHelper<E> personHelper;

    public PersonEntityAdapter(E entity) {
        personHelper = new PersonHelper<E>(entity);
    }

    public E getPersonEntity(){
        return personHelper.getEntity();
    }

    public PersonEntity.CredentialsType getCredentialsType() {
        return personHelper.getCredentialsType();
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        personHelper.setCredentialsType(credentialsType);
        personHelper.typeChange();
    }

    public String getCredentialsNumber() {
        return personHelper.getCredentialsNumber();
    }

    public void setCredentialsNumber(String credentialsNumber) {
        personHelper.setCredentialsNumber(credentialsNumber);
        personHelper.numberChange();
    }

    public String getPersonName() {
        return personHelper.getPersonName();
    }

    public void setPersonName(String name) {
        personHelper.setPersonName(name);
    }


    public boolean isManager(){
        return personHelper.isManager();
    }
}

package com.dgsoft.common.system;

/**
 * Created by cooper on 8/20/14.
 */
public interface PersonEntity {

    public enum CredentialsType{
        MASTER_ID,COMPANY_CODE,SOLDIER_CARD,PASSPORT,OTHER
    }

    public CredentialsType getCredentialsType();

    public void setCredentialsType(CredentialsType credentialsType);

    public String getCredentialsNumber();

    public void setCredentialsNumber(String credentialsNumber);

    public String getPersonName();

    public void setPersonName(String personName);


}

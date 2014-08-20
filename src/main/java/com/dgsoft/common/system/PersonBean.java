package com.dgsoft.common.system;

/**
 * Created by cooper on 8/20/14.
 */
public interface PersonBean {

    public enum CredentialsType{
        MASTER_ID,OTHER;
    }

    public CredentialsType getCredentialsType();

    public void setCredentialsType(CredentialsType credentialsType);

    public String getCredentialsNumber();

    public void setCredentialsNumber(String credentialsNumber);

}

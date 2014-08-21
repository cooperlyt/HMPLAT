package com.dgsoft.common.system;

import com.dgsoft.common.EntityHomeAdapter;
import com.dgsoft.common.system.action.PersonHome;
import com.dgsoft.common.system.model.PersonId;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 8/21/14.
 */
@Scope(ScopeType.CONVERSATION)
public abstract class PersonEntityHelper<E extends PersonEntity> {

    protected abstract EntityHomeAdapter<E> getEntityHome();

    @In
    private PersonHome personHome;

    public PersonEntity.CredentialsType getCredentialsType(){
       return getEntityHome().getInstance().getCredentialsType();
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType){
        getEntityHome().getInstance().setCredentialsType(credentialsType);
    }

    public String getCredentialsNumber(){
        return getEntityHome().getInstance().getCredentialsNumber();
    }

    public void setCredentialsNumber(String credentialsNumber){
        getEntityHome().getInstance().setCredentialsNumber(credentialsNumber);
    }

    private boolean isManager = false;

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void readFromCard(){

        isManager = true;
    }

    public void vaildCerdentials(){
        personHome.setId(new PersonId(getCredentialsType(),getCredentialsNumber()));
        try{
            getEntityHome().getInstance().setPersonName(personHome.getInstance().getName());
            isManager = true;
        }catch (javax.persistence.EntityNotFoundException e){
            isManager = false;
        }
    }

    public void clear(){

        personHome.clearInstance();
        isManager = false;
    }


}

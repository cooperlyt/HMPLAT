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
public abstract class PersonEntityHelper<E extends PersonEntity> extends PersonHelper<E>{

    protected PersonEntityHelper() {
    }

    protected abstract EntityHomeAdapter<E> getEntityHome();

//    private PersonHome getPersonHome(){
//       return (PersonHome) Component.getInstance("personHome",true,true);
//    }

    @Override
    public E getEntity(){
        return getEntityHome().getInstance();
    }

    @Override
    public void setEntity(E entity){
        throw new IllegalArgumentException("canot call this func");
    }


    public void clear() {

        getEntityHome().clearInstance();
        setManager(false);
    }


}

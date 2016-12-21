package com.dgsoft.common.helper;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.contexts.Contexts;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 10/19/13
 * Time: 5:37 PM
 */
@Name("actionExecuteState")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class ActionExecuteState {

    private String lastState = null;


    @BypassInterceptors
    public String getLastState() {
        return lastState;
    }

    public void setLastState(String lastState) {
        this.lastState = lastState;
    }

    public String clearState(){
        lastState = null;
        return "cleared";
    }

    public void setState(String state){
        this.lastState = state;
    }

    public void actionExecute(){
        lastState = "success";
    }


    public static ActionExecuteState instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (ActionExecuteState) Component.getInstance(ActionExecuteState.class, true);
    }

}

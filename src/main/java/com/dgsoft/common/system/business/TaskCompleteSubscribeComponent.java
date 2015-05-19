package com.dgsoft.common.system.business;

/**
 * Created by cooper on 5/15/15.
 */
public interface TaskCompleteSubscribeComponent {


    public abstract TaskSubscribeComponent.ValidResult valid();


    public abstract void complete();

}

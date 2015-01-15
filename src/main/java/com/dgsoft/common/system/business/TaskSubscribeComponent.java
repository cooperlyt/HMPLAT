package com.dgsoft.common.system.business;

/**
 * Created by cooper on 8/25/14.
 */
public interface TaskSubscribeComponent {

    public abstract void initSubscribe();

    // success return "success"
    public abstract String validSubscribe();


    // success return "saved"
    public abstract String saveSubscribe();



}

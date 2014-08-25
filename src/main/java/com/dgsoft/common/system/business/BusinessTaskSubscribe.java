package com.dgsoft.common.system.business;

/**
 * Created by cooper on 8/25/14.
 */
public interface BusinessTaskSubscribe {

    public abstract void init();

    public abstract boolean valid();

    public abstract boolean wire();

}

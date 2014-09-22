package com.dgsoft.common.system;

import com.dgsoft.common.EntityHomeAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-19
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public class PersonEntityHomeHelper<E extends PersonEntity> extends PersonEntityHelper<E>{

    public PersonEntityHomeHelper(EntityHomeAdapter<E> entityHome) {
        this.entityHome = entityHome;
    }

    private EntityHomeAdapter<E> entityHome;

    @Override
    protected EntityHomeAdapter<E> getEntityHome() {
        return entityHome;
    }
}

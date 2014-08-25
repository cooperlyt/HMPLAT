package com.dgsoft.house.owner;

import com.dgsoft.common.EntityHomeAdapter;

/**
 * Created by cooper on 8/25/14.
 */
public class OwnerEntityHome<E> extends EntityHomeAdapter<E> {

    @Override
    protected String getPersistenceContextName(){
        return "ownerEntityManager";
    }

}

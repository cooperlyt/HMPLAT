package com.dgsoft.house.owner;

import com.dgsoft.common.EntityQueryAdapter;

/**
 * Created by cooper on 8/25/14.
 */
public class OwnerEntityQuery<E> extends EntityQueryAdapter<E> {


    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }
}

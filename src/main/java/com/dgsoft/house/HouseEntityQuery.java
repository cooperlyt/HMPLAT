package com.dgsoft.house;

import com.dgsoft.common.EntityQueryAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-5
 * Time: 上午9:56
 */
public class HouseEntityQuery<E> extends EntityQueryAdapter<E> {

    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }
}

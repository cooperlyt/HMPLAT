package com.dgsoft.house;

import com.dgsoft.common.EntityHomeAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-5
 * Time: 上午9:51
 */
public class HouseEntityHome<E> extends EntityHomeAdapter<E> {


    @Override
    protected String getPersistenceContextName(){
        return "houseEntityManager";
    }
}

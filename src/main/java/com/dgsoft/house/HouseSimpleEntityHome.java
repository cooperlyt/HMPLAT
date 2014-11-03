package com.dgsoft.house;

import com.dgsoft.common.SimpleEntityHome;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-5
 * Time: 下午4:02
 */
public class HouseSimpleEntityHome<E> extends SimpleEntityHome<E>{

    @Override
    protected String getPersistenceContextName() {
        return "houseEntityManager";
    }

}

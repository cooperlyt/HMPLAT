package com.dgsoft.common.system;

import com.dgsoft.common.system.model.Person;
import org.jboss.seam.Component;

import javax.persistence.EntityManager;

/**
 * Created by cooper on 9/19/14.
 */
public class PersonHelper<E extends PersonEntity> extends PersonHelperBase<E>  {

    //TODO PersonHelper 存在的意义 uuid 的生成 uuid 为 push 的address 为防止 push 不释放 应重复使用 UUID

    public PersonHelper(E entity) {
        super(entity);
    }

    @Override
    protected PersonIDCard findStorePersonIDCard(String s) {
        return getSystemEntityManager().find(Person.class,s);
    }

    private EntityManager getSystemEntityManager(){
        return (EntityManager) Component.getInstance("systemEntityManager",true,true);
    }


}

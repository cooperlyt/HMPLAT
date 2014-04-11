package com.dgsoft.house;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.ui.JpaEntityLoader;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 11/04/14
 * Time: 13:24
 */
@Name("houseEntityLoader")
public class HouseEntityLoader extends JpaEntityLoader {

    @Override
    protected String getPersistenceContextName()
    {
        return "houseEntityManager";
    }

    @In
    private EntityManager houseEntityManager;

    @Override
    public EntityManager getPersistenceContext(){

        return houseEntityManager;
    }
}

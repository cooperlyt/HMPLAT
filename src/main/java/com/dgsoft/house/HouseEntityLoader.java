package com.dgsoft.house;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Contexts;
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

    public static HouseEntityLoader instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (HouseEntityLoader) Component.getInstance(HouseEntityLoader.class, true);
    }
}
